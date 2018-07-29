package app.xlui.example.conditional.conf;

import app.xlui.example.conditional.condition.WindowsCondition;
import app.xlui.example.conditional.service.ListService;
import app.xlui.example.conditional.service.impl.LinuxListService;
import app.xlui.example.conditional.condition.LinuxCondition;
import app.xlui.example.conditional.service.impl.WindowsListService;
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
