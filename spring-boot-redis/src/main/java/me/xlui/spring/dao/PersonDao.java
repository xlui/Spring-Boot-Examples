package me.xlui.spring.dao;

import me.xlui.spring.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

//	@Resource(name = "stringRedisTemplate")
//	ValueOperations<String, String> valueOperationsString;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

//	@Resource(name = "redisTemplate")
//	private ValueOperations<Object, Object> valueOperations;

	public void stringRedisTemplateDao() {
		stringRedisTemplate.opsForValue().set("xx", "yy");
//		valueOperationsString.set("xx", "yy");
	}

	public void save(Person person) {
		redisTemplate.opsForValue().set(person.getId(), person);
//		valueOperations.set(person.getId(), person);
	}

	public String getString() {
		return stringRedisTemplate.opsForValue().get("xx");
//		return valueOperationsString.get("xx");
	}

	public Person getPerson() {
		return (Person) redisTemplate.opsForValue().get("1");
//		return (Person)valueOperations.get("1");
	}
}
