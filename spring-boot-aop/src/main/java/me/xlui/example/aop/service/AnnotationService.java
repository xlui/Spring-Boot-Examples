package me.xlui.example.aop.service;

import me.xlui.example.aop.annotation.Action;
import org.springframework.stereotype.Service;

/**
 * 使用注解的被拦截类
 */
@Service
public class AnnotationService {
	@Action(name = "注解式拦截的 add 操作")
	public void add() {}
}
