package me.xlui.example.service.impl;

import me.xlui.example.service.ListService;

public class WindowsListService implements ListService {
    @Override
    public String showListCmd() {
        return "dir";
    }
}
