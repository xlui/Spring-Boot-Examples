package me.xlui.spring.web;

import me.xlui.spring.dao.PersonDao;
import me.xlui.spring.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	@Autowired
	private PersonDao personDao;

	@RequestMapping("/set")
	public void set() {
		Person person = new Person("1", "xlui", 20);
		personDao.save(person);
		personDao.stringRedisTemplateDao();
	}

	@RequestMapping("/getStr")
	public String getStr() {
		return personDao.getString();
	}

	@RequestMapping("/getPerson")
	public Person getPerson() {
		return personDao.getPerson();
	}
}
