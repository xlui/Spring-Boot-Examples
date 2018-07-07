package me.xlui.example.conditional.service.impl;

import me.xlui.example.conditional.service.ListService;

public class WindowsListService implements ListService {
    @Override
    public String showListCmd() {
        return "dir";
    }
}
