package me.xlui.example.jpa.web;

import me.xlui.example.jpa.entity.Tag;
import me.xlui.example.jpa.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagRepository tagRepository;

    @RequestMapping("/tags")
    public List<Tag> all() {
        return tagRepository.findAll();
    }
}
