# AOP 

自定义了拦截注解：HelloAction

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HelloAction {
    String name();
}
```

定义了通过两种方式被拦截的类 HelloAnnotationService 和 HelloMethodService：

```java
@Service
public class HelloAnnotationService {
    @HelloAction(name = "注解式拦截的 add 操作")
    public void add() {}
}
```

```java
@Service
public class HelloMethodService {
    public void add() {}
}
```

定义了切面：HelloServiceMonitor

```java
@Aspect
@Component
public class HelloServiceMonitor {
Actionublic void annotationPointCut() {}

    @After("@annActionic void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HelloAction action = method.getAnnotation(HelloAction.class);
        System.out.println("注解式拦截 " + action.name());
    }

    @Before("execution(* me.xlui.spring.service.HelloMethodService.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截 " + method.getName());
    }
}
```