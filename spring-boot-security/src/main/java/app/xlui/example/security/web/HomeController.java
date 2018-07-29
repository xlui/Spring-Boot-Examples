package app.xlui.example.security.web;

import app.xlui.example.security.entity.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String index(Model model) {
        Message message = new Message("测试标题", "测试内容", "额外信息，只对管理员显示");
        model.addAttribute("msg", message);
        return "index";
    }
}
