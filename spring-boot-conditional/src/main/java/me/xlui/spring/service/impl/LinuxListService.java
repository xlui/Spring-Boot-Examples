package me.xlui.spring.service.impl;

import me.xlui.spring.service.ListService;

public class LinuxListService implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
