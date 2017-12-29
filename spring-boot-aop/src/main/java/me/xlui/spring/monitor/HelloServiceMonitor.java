package me.xlui.spring.monitor;

import me.xlui.spring.interceptor.HelloAction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义切面
 */
@Aspect
@Component
public class HelloServiceMonitor {
//	@Pointcut("@annotation(me.xlui.spring.interceptor.HelloAction)")
//	public void annotationPointCut() {}

	@After("@annotation(me.xlui.spring.interceptor.HelloAction)")
	public void after(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		HelloAction helloAction = method.getAnnotation(HelloAction.class);
		System.out.println("注解式拦截 " + helloAction.name());
	}

	@Before("execution(* me.xlui.spring.service.HelloMethodService.*(..))")
	public void before(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		System.out.println("方法规则式拦截 " + method.getName());
	}
}
