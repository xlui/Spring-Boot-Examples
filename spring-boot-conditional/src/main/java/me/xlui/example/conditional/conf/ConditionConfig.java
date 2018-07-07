package me.xlui.example.conditional.conf;

import me.xlui.example.conditional.condition.LinuxCondition;
import me.xlui.example.conditional.condition.WindowsCondition;
import me.xlui.example.conditional.service.ListService;
import me.xlui.example.conditional.service.impl.LinuxListService;
import me.xlui.example.conditional.service.impl.WindowsListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {
    @Bean
    @Conditional(WindowsCondition.class)
    public ListService windowsListService() {
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxListService() {
        return new LinuxListService();
    }
}
