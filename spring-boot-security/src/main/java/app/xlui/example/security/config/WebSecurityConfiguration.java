package app.xlui.example.security.config;

import app.xlui.example.security.service.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Bean
	protected CustomUserService customUserService() {
		return new CustomUserService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 添加自定义的认证
//		auth.inMemoryAuthentication()
//				.withUser("username").password("password").roles("asa").authorities("authority_test");
//				.withUser("username").password("password").roles("asa");
		auth.userDetailsService(customUserService());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest()	// 所有的请求都需要认证后才能访问
					.authenticated()
				.and()
				.formLogin()	// 定制登录行为
					.loginPage("/login")
					.failureUrl("/login?error")
					.permitAll()
				.and()
				.logout()		// 定制注销行为
					.permitAll()
				.and()
				.csrf()
					.disable();
	}
}
