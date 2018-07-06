package me.xlui.example.service.impl;

import me.xlui.example.service.ListService;

public class LinuxListService implements ListService {
    @Override
    public String showListCmd() {
        return "ls";
    }
}
