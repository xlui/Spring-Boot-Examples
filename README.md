# Spring Boot Examples

[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/xlui/Spring-Boot-Examples)

Examples for how to integrate Spring Boot with some popular components.

## Table of Contents

- [ActiveMQ](#activemq)
- [AOP](#aop)
- [Cache](#cache)
- [Conditional](#conditional)
- [Docker](#docker)
- [JPA](#jpa)
- [Kafka](#kafka)
- [MongoDB](#mongodb)
- [Mybatis](#mybatis)
- [RabbitMQ](#rabbitmq)
- [Redis](#redis)
- [Schedule](#schedule)
- [Security](#security)
- [Shiro](#shiro)
- [Starter](#starter)
- [Swagger](#swagger)

## ActiveMQ

JMS(Java Message Service) enable Java application to send message across different application. JMS is based on JVM message proxy standard, and ActiveMQ is a popular implement of JMS.

Not that in this example I use maven for multi-modules.

## AOP

IoC(Inversion of Control) and AOP(Aspect-Oriented Programming) is the two core concepts in the Spring Framework, and I will use some code to show how to simply use AOP in Spring.

In the beginning, we show import `spring-boot-starter-aop`.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

Spring introduce some annotations from AspectJ but the implement of AOP in Spring is **dynamic proxy**, which is **static implantation** in AspectJ. There are much ways to define aspect and in the following I have only shown two ways:

```java
@Aspect
@Component
public class ServiceMonitor {
//	@Pointcut("@annotation(me.xlui.spring.annotation.Action)")
//	public void annotationPointCut() {}

    @After("@annotation(me.xlui.spring.annotation.Action)")
    public void after(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println(action.name());
    }

    @Before("execution(* me.xlui.spring.service.MethodService.*(..))")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println("方法规则式拦截 " + method.getName());
    }
}
```

The first one is through annotation and the following is through the special grammar of aspect in Spring.

For more references about how to define a aspect, see the [docs](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop-ataspectj).

## Cache

Cache is a very common way to speed up. Using available cache, client does not need to request resource from server again. This provides a better experience.

Spring supports various cache and defines a universal interface to use cache in Spring Framework. Also, spring provides 4 annotations to make it easier to operate cache.

As the example, I'm using Redis as my cache provider. In order to use Redis as you cache provider, you just need to import it through maven or gradle.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

The implement of service(I used to use a service layer above the repository).

```java
@CachePut(value = "people", key = "#person.id")
// CachePut will always run the method and put the result to cache.
// the `value` of annotation specifies the name of the cache
// and `key` in annotation specifies the key in cache
public Person save(Person person) {
    Person p = DB.map.put(person.getId(), person);
    System.out.println("为 key 为 [" + p.getId() + "] 的数据更新缓存");
    return p;
}

@CacheEvict(value = "people", key = "#id")
// CahceEvict will remove `key` specified cache in cache
public void remove(Integer id) {
    System.out.println("删除了 key 为 [" + id + "] 的数据缓存");
    DB.map.remove(id);
}

@Override
@Cacheable(value = "people", key = "#person.id")
// Cacheable will not run if `key` specified cache is available. And if it does not exist in cache, the method will be run and the result will be added to cache.
public Person find(Person person) {
    Person p = DB.map.get(person.getId());
    System.out.println("为 key 为 [" + p.getId() + "] 的数据做了缓存");
    return p;
}
```

## Conditional

`@Conditional` and the related to annotations provide a simple way to initialize Spring Bean for different conditions. Only when the condition established will spring initialize bean or do some other things. This is used widely in spring's autoconfigure package(spring-boot-autoconfigure) and is the foundation of spring boot.

## Docker

Spring Boot have integrated a tomcat container as default. So when deploying spring boot applications, we just need to run:

```bash
java -jar xxxx.jar
```

We does not need to package spring boot application in `war` format, and place it under tomcat.

A better way to deploy spring boot application is **docker**. Through docker, we can manage and update spring boot applications easily.

For spring boot applications which use maven as build tools, there is a plugin: `docker-maven-plugin`.

1. Add `docker-maven-plugin` in `pom.xml`:

```xml
<properties>
    <docker.image.prefix>xlui</docker.image.prefix>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <!-- Docker Maven Plugin -->
        <plugin>
            <groupId>com.spotify</groupId>
            <artifactId>docker-maven-plugin</artifactId>
            <version>1.1.1</version>
            <!-- Run `mvn package` will also run: -->
            <!-- `docker:removeImage` `docker:build` -->
            <executions>
                <execution>
                    <id>build-image</id>
                    <phase>package</phase>
                    <goals>
                        <goal>removeImage</goal>
                        <goal>build</goal>
                    </goals>
                </execution>
            </executions>
            <!-- Image configurations -->
            <configuration>
                <imageName>${docker.image.prefix}/docker-example</imageName>
                <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>
                <resources>
                    <resource>
                        <targetPath>/</targetPath>
                        <directory>${project.build.directory}</directory>
                        <include>${project.build.finalName}.jar</include>
                    </resource>
                </resources>
            </configuration>
        </plugin>
    </plugins>
</build>
```

2. Create `Dockerfile` under `${project.basedir}/src/main/docker`:

```dockerfile
FROM openjdk:8-jdk-alpine
# create a temporary folder in host under /var/lib/docker and link it to /tmp in docker
VOLUME /tmp
ADD docker-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "-Dserver.port=8080", "/app.jar"]
```

3. Build the image

```bash
sudo mvn clean package -DskipTests
```

## JPA

JPA(Java Persistence API) provides a POJO persistence model for object-relational mapping. And Spring Data JPA provides us a very easy, no-sql way to operate database.

## Kafka

Apache Kafka is an open-source stream-processing software platform. 

## MongoDB

MongoDB is a no-sql database, which is different from MySQL. And it is very suitable for logs and applications does not need transactional much and so on. Spring Framework provides common API for operate MongoDB and MySQL.

## Mybatis

Mybatis is another popular relational database ORM framework. With mybatis, we can customize our sql statement, which is very important in some scene.

## RabbitMQ

RabbitMQ is another asynchronous message provider just like JMS(ActiveMQ).

## Redis

Redis is an open source (BSD licensed), in-memory data structure store, used as a database, cache and message broker. It supports data structures such as `strings`, `hashes`, `lists`, `sets`, `sorted sets` with range queries, `bitmaps`, `hyperloglogs` and geospatial indexes with radius queries.

## Schedule

Spring Schedule provide us a simple and clear way to implement cron job. What we need do is to annotate `@EnableScheduling` at a configuration class and then use `@Scheduled` upon a method to specify our cron jobs.

As for example, I have created a cron job project that will notify users when they are going to expire(such as subscribing). The syntax of `@Scheduled` is clear enough in the documents so I'll not introduce it again.

## Security

Spring Security is a authentication, authorization and permission-control framework provided by spring.

## Shiro

Apache Shiro is another security framework do same things like Spring Security.

## Starter

This example shoulds how to create your own spring-boot-starter.

## Swagger

Swagger is a framework to provide visible API description through annotations.
