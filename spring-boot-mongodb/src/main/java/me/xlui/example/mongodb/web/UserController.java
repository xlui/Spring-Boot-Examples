package me.xlui.example.mongodb.web;

import me.xlui.example.mongodb.document.User;
import me.xlui.example.mongodb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * 每次都会新增一个文档
     *
     * @return 新增的文档
     */
    @RequestMapping("/save")
    public User save() {
        User user = new User("1", "dev");
        return userRepository.save(user);
    }

    @RequestMapping("/delete")
    public String delete() {
        User user = userRepository.findByUsername("1");
        if (user == null) {
            return "No such user!";
        } else {
            userRepository.deleteById(user.getId());
            return "succ!";
        }
    }

    @RequestMapping("/update")
    public User modify() {
        User user = userRepository.findByUsername("1");
        user.setPassword("newPasswd");
        return userRepository.save(user);
    }

    @RequestMapping("/query")
    public User query() {
        return userRepository.findByUsername("1");
    }
}
