package me.xlui.example.shiro.web;

import me.xlui.example.shiro.entity.User;
import me.xlui.example.shiro.repository.UserRepository;
import me.xlui.example.shiro.utils.LogUtil;
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
    private final UserRepository userRepository;

    @Autowired
    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping({"/", "/index"})
    public String index() {
        LogUtil.getLogger().info("HelloController.index");
        return "index";
    }

    /**
     * 访问此 url 进行密码加密存储
     */
    @RequestMapping("/en")
    @ResponseBody
    public String encrypt() {
        User user = userRepository.findByUsername("admin");
        user.setSalt(user.getUsername());
        user.setPassword((new SimpleHash("MD5", user.getPassword(), ByteSource.Util.bytes(user.getSalt()), 1024)).toString());
        userRepository.save(user);
        return "succ";
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

    @RequestMapping("/userAdd")
    @RequiresPermissions("user add")
    public String userAdd() {
        return "userAdd";
    }

    @RequestMapping("/userDel")
    @RequiresPermissions("user del")
    public String userDel() {
        return "userDel";
    }

    @RequestMapping("/userInfo")
    @RequiresPermissions("user info")
    public String userInfo() {
        return "userInfo";
    }
}
