package me.xlui.spring.service;

import me.xlui.spring.interceptor.HelloAction;
import org.springframework.stereotype.Service;

/**
 * 使用注解的被拦截类
 */
@Service
public class HelloAnnotationService {
	@HelloAction(name = "注解式拦截的 add 操作")
	public void add() {}
}
