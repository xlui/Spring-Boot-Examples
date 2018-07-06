# Spring Boot 集成 Shiro 权限管理与密码加盐加密存储

在 Spring 中，流行的涉及权限管理的框架有两个：Spring Security 和 Apache Shiro。但是去了解一下 Spring Security 就知道，简单的权限管理根本用不到那么复杂的功能。在自己的项目中，我更倾向于使用简单明了的 Apache Shiro。

我们以最常见的`用户、角色、权限`关系做例子。一个用户有多个角色、一个角色有多个用户、一个角色有多个权限、一个权限有多个角色。即用户与角色、角色与权限是多对多关系。

## 引入 shiro-spring 包

### pom包依赖：

重要的是 shiro-spring 这个包。

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- MariaDB -->
<dependency>
  <groupId>org.mariadb.jdbc</groupId>
  <artifactId>mariadb-java-client</artifactId>
  <version>RELEASE</version>
</dependency>

<!-- shiro -->
<dependency>
  <groupId>org.apache.shiro</groupId>
  <artifactId>shiro-spring</artifactId>
  <version>RELEASE</version>
</dependency>
```

### 配置文件

```
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/test
spring.datasource.username=test
spring.datasource.password=test

spring.jpa.database=mysql
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

spring.thymeleaf.cache=false

spring.jackson.serialization.indent-output=true
```

## 实体类

### 用户类

```java
package me.xlui.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shiro_user")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  private String password;
  private String salt;    // 密码加盐

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "shiro_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<Role> roleList;

  @Override
  public String toString() {
    return "User[id = " + id + ", username = " + username + ", password = " + password + ", salt = " + salt + "]";
  }

  public User() {
    super();
  }

// 省略 getter 和 setter
}
```

### 角色类

```java
package me.xlui.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shiro_role")
public class Role implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;
  private String role;

  @ManyToMany
  @JoinTable(name = "shiro_user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private List<User> userList;

  @ManyToMany
  @JoinTable(name = "shiro_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
  private List<Permission> permissionList;

  public Role() {
    super();
  }

// 省略 getter 和 setter
}
```

### 权限类

```java
package me.xlui.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shiro_permission")
public class Permission implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;
  private String permission;

  @ManyToMany
  @JoinTable(name = "shiro_role_permission", joinColumns = {@JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
  private List<Role> roleList;

  public Permission() {
    super();
  }

// 省略 getter 和 setter
}
```

### 初始化数据库表

```sql
INSERT INTO shiro_user (id, password, salt, username) VALUES
  (1, "dev", "salt", "admin");

INSERT INTO shiro_role (id, role) VALUES
  (1, "admin"),
  (2, "normal");

INSERT INTO shiro_permission (id, permission) VALUES
  (1, "user info"),
  (2, "user add"),
  (3, "user del");

INSERT INTO shiro_user_role (user_id, role_id) VALUES
  (1, 1);

INSERT INTO shiro_role_permission (permission_id, role_id) VALUES
  (1, 1),
  (2, 1);
```

### 查询接口

UserRepository:

```java
package me.xlui.example.repository;

import me.xlui.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
}
```

其他省略。

## Shiro 配置

Apache Shiro 核心通过 Filter 实现，是基于 URL 规则来进行过滤和权限校验。我们通过注入类来进行 Shiro 的配置：

```java
package me.xlui.example.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfiguration {
  /**
    * 开启对注解 `@RequirePermission` 的支持
    */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }

  /**
    * 自己实现的 Realm，Shiro 的认证最终都交给 Realm 进行执行了。我们需要自己实现一个 Realm，继承自 AuthrozingRealm
    */
  @Bean
  public MyShiroRealm myShiroRealm() {
    return new MyShiroRealm();
  }

  @Bean
  public SecurityManager securityManager() {
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(myShiroRealm());
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
    filterChainDefinitionMap.put("/logout", "logout");
    filterChainDefinitionMap.put("/static", "anon");
    // 将 /static 设置为 anon，避免登录后下载 favicon
    filterChainDefinitionMap.put("/**", "authc");
    // authc 表示需要验证身份才能访问，anon 表示不需要

    shiroFilterFactoryBean.setLoginUrl("/login");
    // 如果不设置，默认 Shiro 会寻找 classpath:/template/login.jsp 文件
    shiroFilterFactoryBean.setSuccessUrl("/index");
    // 成功登陆后跳转

    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }
}
```

通过 `ShiroFilterFactoryBean` 来处理拦截资源文件的问题（单独的 ShiroFilterFactoryBean 配置会出错，因为 Shiro 还需要 SecurityManager）。

ShiroFilterFactory 中已经由 Shiro 官方实现的过滤器（只列举常用的）：

Filter Name|作用
---|---
anon|匿名可访问
authc|需要认证
user|配置记住我或认证可访问

## Shiro 认证和授权

Shiro 的认证、授权最终都交给 Realm 来处理，同时在 Shiro 中，用户、角色和权限等信息也是在 Realm 中获取。我们要做的是自定义一个类，继承抽象基类 AuthorizingRealm：

```java
package me.xlui.example.config;

import me.xlui.example.entity.Permission;
import me.xlui.example.entity.Role;
import me.xlui.example.entity.User;
import me.xlui.example.repository.UserRepository;
import me.xlui.example.utils.LogUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Shiro 主要配置
 */
public class MyShiroRealm extends AuthorizingRealm {
  @Autowired
  private UserRepository userRepository;

  /**
    * 负责授权
    */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    LogUtil.getLogger().info("权限配置：MyShiroRealm.doGetAuthorizationInfo");
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    User user = (User) principalCollection.getPrimaryPrincipal();
    LogUtil.getLogger().info("为用户 " + user.getUsername() + " 进行权限配置");

    for (Role role : user.getRoleList()) {
      authorizationInfo.addRole(role.getRole());
      for (Permission permission : role.getPermissionList()) {
        authorizationInfo.addStringPermission(permission.getPermission());
      }
    }

    return authorizationInfo;
  }

  /**
    * 负责身份认证
    */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    LogUtil.getLogger().info("开始身份认证");
    String username = (String) authenticationToken.getPrincipal();
    LogUtil.getLogger().info("输入得到的用户名：" + username);
    User user = userRepository.findByUsername(username);
    // 从数据库中查找 user

    if (user == null) {
      return null;
    }

    LogUtil.getLogger().info("用户信息：\n" + user.toString());
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        user,
        user.getPassword(),
  //    ByteSource.Util.bytes(user.getSalt()),  如果密码需要加盐验证，需要使用这个构造方法，后面会讲到。
        getName()
    );
    return authenticationInfo;
  }
}
```

继承 AuthorizingRealm 类需要实现两个方法：doGetAuthorizationInfo() 和 doGetAuthenticationInfo()。`doGetAuthorizationInfo()` 负责权限管理，即为用户设置允许的权限，`doGetAuthenticationInfo()` 方法负责身份认证，即检验用户名密码的正确性。

```java
SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
    user,
    user.getPassword(),
//  ByteSource.Util.bytes(user.getSalt()),  如果密码需要加盐验证，需要使用这个构造方法，后面会讲到。
    getName()
);
```

默认使用 `CredentialsMatcher` 来进行用户名密码确认，如果觉得默认的不好可以自己手动实现，下面讲密码加密存储会涉及到。注释的一行是密码加密时使用的盐，如果是明文密码去掉注释的一行即可。

接下来需要把自定义的 Realm 注入到 SecurityManager 中，代码在 ShiroConfiguration 类中已经实现：

```java
@Bean
public MyShiroRealm myShiroRealm() {
  return new MyShiroRealm();
}

@Bean
public SecurityManager securityManager() {
  DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
  securityManager.setRealm(myShiroRealm());
  return securityManager;
}
```

## 测试

经过上面的操作 Shiro 的集成基本已经是完成了，下面进行测试：

### 控制器

```java
package me.xlui.example.web;

import me.xlui.example.entity.User;
import me.xlui.example.repository.UserRepository;
import me.xlui.example.utils.LogUtil;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class HelloController {
  @Autowired
  private UserRepository userRepository;

  @RequestMapping({"/", "/index"})
  public String index() {
    LogUtil.getLogger().info("HelloController.index");
    return "index";
  }

  @RequestMapping("/login")
  public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
    LogUtil.getLogger().info("HelloController.login");
    String exception = (String) request.getAttribute("shiroLoginFailure");
    String msg = "";
    if (exception != null) {
      if (UnknownAccountException.class.getName().equals(exception)) {
        LogUtil.getLogger().info("账户不存在！");
        msg = "账户不存在";
      } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
        // 实际应用的时候写 "用户名或密码错误"
        LogUtil.getLogger().info("密码不正确！");
        msg = "密码错误";
      } else {
        LogUtil.getLogger().info("发生异常：" + exception);
        msg = "其他异常";
      }
    }

    map.put("msg", msg);
    return "login";
  }
}
```

### 模板

index.html:

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Index</title>
</head>
<body>
<p>这是 Index 页！</p>
</body>
</html>
```

login.html:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title>Login</title>
</head>
<body>
错误信息：<h4 style="color: red;" th:text="${msg}"></h4>
<form th:action="@{/login}" method="post">
    <p>用户名：<input type="text" name="username" autofocus="autofocus"/></p>
    <p>密码：<input type="password" name="password"/></p>
    <p><input type="submit" value="登录"/></p>
</form>
</body>
</html>
```

### 运行

运行项目，访问 http://localhost:8080/。

## 密码加盐加密存储

实际应用中我们往往不会直接明文存储密码，因为这样非常不安全。而单纯的使用 MD5、SHA 之类的算法加密密码会存在数据库中两个密码相同用户的 `password` 字段也相同的情况，这样也很容易被撞库攻击。一种更安全的方式是加盐加密。

加盐加密的思路是在使用 MD5、SHA 之类算法的时候在用户的密码字段加一个随机、唯一的字符串（盐），这样生成的加密密码串几乎不可能存在相同的。即使是两个相同的密码，因为盐的不同，生成的密码串也是万万不同的。

### 生成加盐密码串

我采用的是访问特定 url 生成加盐密码串存储，实际应用的时候可以直接在用户注册或者修改密码的时候生成。

```java
// HelloController
@RequestMapping("/en")
@ResponseBody
public String encrypt() {
  User user = userRepository.findByUsername("admin");
  user.setSalt(user.getUsername());
  user.setPassword((new SimpleHash("MD5", user.getPassword(), ByteSource.Util.bytes(user.getSalt()), 1024)).toString());
  userRepository.save(user);
  return "succ";
}
```

`SimpleHash` 是 Shiro 提供给我们的加密类，第一个参数是加密算法名，第二个参数是原密码，第三个参数是盐，因为在 Realm 中向 `SimpleAuthenticationInfo` 类传递参数时需要 ByteSource 类实例，所以我们在这里使用了相同的格式。实际上 SimpleHash 类对盐的具体类型没有要求，其形参的类型是 Object。第四个参数是加密的次数。

我们用自己的方式生成了加盐加密的密码串，接下来还需要告诉 Shiro 使用这种方式验证。

### 注入加密方式

本来我们应该编写一个加密算法类，但是 Shiro 已经替我们实现了，`HashedCredentialsMatcher`，我们只需要注入使用即可。有两种使用方式：

（1）重写 `MyShiroRealm`（自定义 Realm 类）的 `setCredentialsMatcher` 方法：

```java
public class MyShiroRealm extends AuthorizingRealm {
// ...

  @Override
  public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
    // 重写 setCredentialsMatcher 方法为自定义的 Realm 设置 hash 验证方法
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    hashedCredentialsMatcher.setHashAlgorithmName("MD5");
    hashedCredentialsMatcher.setHashIterations(1024);
    super.setCredentialsMatcher(hashedCredentialsMatcher);
  }
}
```

（2）在 `ShiroConfiguration` 中注入：

```java
@Configuration
public class ShiroConfiguration {
// ...

  @Bean
  public HashedCredentialsMatcher hashedCredentialsMatcher(){
    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    hashedCredentialsMatcher.setHashAlgorithmName("md5");
    hashedCredentialsMatcher.setHashIterations(1024);
    return hashedCredentialsMatcher;
  }

  @Bean
  public MyShiroRealm myShiroRealm() {
    MyShiroRealm myShiroRealm = new MyShiroRealm();
    myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    return myShiroRealm;
  }
}
```

两种方法无太大优劣，都可以成功告知 Shiro 使用这种方式进行加盐密码验证。

如果觉得默认的 `HashedCredentialsMatcher` 不好，可以自己动手实现一个，继承 `CredentialsMatcher` 接口，然后按照上面的方法集成即可。

### 验证

运行程序，访问 http://localhost:8080/en，跳转到登录，登录后返回，对密码进行加盐存储。

查看数据库中用户表相应字段是否更新。

关闭浏览器，重新访问 http://localhost:8080 使用原用户名密码成功登录。

## 吐槽

Spring Boot 和 Shiro 似乎存在一些问题。我一般开发的时候都在配置文件（application.properties）中这样设置：

```
spring.jpa.hibernate.ddl-auto=create
```

然后再 classpath 也就是 src/main/resources 下新建 data.sql。这样 Spring Boot 在启动的时候就会删除所有相关表重建并且执行 data.sql 中的语句进行初始化。

但是在使用 Shiro 的情况下 data.sql 一直无法成功执行。Google 和 StackOverflow 都没有发现理想的回答[摊手]。

还有就是关于密码加盐存储这一点，百度到的博客基本就是抄来抄去，大部分只提了如何给密码加盐，基本没提到加盐存储之后 Shiro 如何验证。

## 源码

源代码已经上传 GitHub：[https://github.com/xlui/Spring-Boot-Examples/tree/master/spring-boot-shiro](https://github.com/xlui/Spring-Boot-Examples/tree/master/spring-boot-shiro)。如果对你有所帮助，不妨留个 star 再走。
