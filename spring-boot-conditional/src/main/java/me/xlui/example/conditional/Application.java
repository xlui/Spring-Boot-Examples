package me.xlui.example.conditional;

import me.xlui.example.conditional.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final ListService listService;

	@Autowired
	public Application(ListService listService) {
		this.listService = listService;
	}

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(listService.showListCmd());
    }
}
