package me.xlui.spring;

import me.xlui.spring.db.DB;
import me.xlui.spring.entity.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
// 开启缓存支持
@EnableCaching
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {
        DB.map.put(1, new Person(1, "alpha"));
        DB.map.put(2, new Person(2, "beta"));
        System.out.println("init succ!");
    }
}
