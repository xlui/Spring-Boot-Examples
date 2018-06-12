# Spring Boot Examples

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/xlui/Spring-Boot-Examples)


Examples for how to use Spring Boot with some popular components.

## Table of Contents

- AOP
- Cache
- Spring Boot JPA
- MongoDB
- Mybatis
- Redis
- Security
- Shiro
- Starter
- Swagger

## AOP

Ioc(Inversion of Control) and AOP(Aspect-Oriented Programming) is the two core concepts in the Spring Framework, and I will use some code to show how to simply use AOP in Spring.

In the beginning, we show import `spring-boot-starter-aop`.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

Spring introduce some annotations from AspectJ but the implement of AOP in Spring is **dynamic**, which is static in AspectJ. There are much ways to define aspect and in the following I have only shown two ways:

```java
@Aspect
@Component
public class ServiceMonitor {
//	@Pointcut("@annotation(me.xlui.spring.annotation.Action)")
//	public void annotationPointCut() {}

    @After("@annotation(me.xlui.spring.annotation.Action)")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println(action.name());
    }

    @Before("execution(* me.xlui.spring.service.MethodService.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截 " + method.getName());
    }
}
```

The first one is through annotation and the following is through the special grammar of aspect in Spring.

For more references about how to define a aspect, see the [docs](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-ataspectj).

## Cache

Cache is a very common way to speed up. Using available cache, client does not need to request resource from server again. This provides a better experience.

Spring supports various cache and defines a universal interface to use cache in Spring Framework. Also, spring provides 4 annotations to make it easier to operate cache.

As the example, I'm using Redis as my cache provider. In order to use Redis as you cache provider, you just need to import it through maven or gradle.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

The implement of service(I used to use a service layer above the repository).

```java
@CachePut(value = "people", key = "#person.id")
// CachePut will always run the method and put the result to cache.
// the `value` of annotation specifies the name of the cache
// and `key` in annotation specifies the key in cache
public Person save(Person person) {
    Person p = DB.map.put(person.getId(), person);
    System.out.println("为 key 为 [" + p.getId() + "] 的数据更新缓存");
    return p;
}

@CacheEvict(value = "people", key = "#id")
// CahceEvict will remove `key` specified cache in cache
public void remove(Integer id) {
    System.out.println("删除了 key 为 [" + id + "] 的数据缓存");
    DB.map.remove(id);
}

@Override
@Cacheable(value = "people", key = "#person.id")
// Cacheable will not run if `key` specified cache is available. And if it does not exist in cache, the method will be run and the result will be added to cache.
public Person find(Person person) {
    Person p = DB.map.get(person.getId());
    System.out.println("为 key 为 [" + p.getId() + "] 的数据做了缓存");
    return p;
}
```

## Spring Boot JPA

JPA(Java Persistence API) provides a POJO persistence model for object-relational mapping. And Spring Data JPA provides us a very easy, no-sql way to operate database.
