package me.xlui.example.aop.aspect;

import me.xlui.example.aop.annotation.Action;
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
public class ServiceMonitor {
//	@Pointcut("@annotation(Action)")
//	public void annotationPointCut() {}

    @After("@annotation(me.xlui.example.aop.annotation.Action)")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println(action.name());
    }

    @Before("execution(* me.xlui.example.aop.service.MethodService.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截 " + method.getName());
    }
}
