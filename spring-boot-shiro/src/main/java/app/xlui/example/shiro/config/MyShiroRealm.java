package app.xlui.example.shiro.config;

import app.xlui.example.shiro.entity.Permission;
import app.xlui.example.shiro.repository.UserRepository;
import app.xlui.example.shiro.entity.Role;
import app.xlui.example.shiro.entity.User;
import app.xlui.example.shiro.utils.LogUtil;
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

        if (user == null) {
            return null;
        }

        LogUtil.getLogger().info("用户信息：\n" + user.toString());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        // 重写 setCredentialsMatcher 方法为自定义的 Realm 设置 hash 验证方法
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
