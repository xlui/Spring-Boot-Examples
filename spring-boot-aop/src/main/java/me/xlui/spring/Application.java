package me.xlui.spring;

import me.xlui.spring.service.HelloAnnotationService;
import me.xlui.spring.service.HelloMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private HelloAnnotationService helloAnnotationService;
	@Autowired
	private HelloMethodService helloMethodService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		helloAnnotationService.add();
		helloMethodService.add();
	}
}
