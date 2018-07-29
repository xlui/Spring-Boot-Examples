package app.xlui.example.conditional.service.impl;

import app.xlui.example.conditional.service.ListService;

public class WindowsListService implements ListService {
    @Override
    public String showListCmd() {
        return "dir";
    }
}
