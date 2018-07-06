package me.xlui.example.web;

import me.xlui.example.entity.Sort;
import me.xlui.example.repository.SortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SortController {
    @Autowired
    private SortRepository sortRepository;

    @RequestMapping("/sorts")
    public List<Sort> all() {
        return sortRepository.findAll();
    }
}
