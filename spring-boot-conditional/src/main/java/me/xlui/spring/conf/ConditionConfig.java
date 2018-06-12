package me.xlui.spring.conf;

import me.xlui.spring.condition.LinuxCondition;
import me.xlui.spring.condition.WindowsCondition;
import me.xlui.spring.service.ListService;
import me.xlui.spring.service.impl.LinuxListService;
import me.xlui.spring.service.impl.WindowsListService;
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
