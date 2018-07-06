package me.xlui.example.dao;

import me.xlui.example.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<Object, Object> redisTemplate;

    //	@Resource(name = "stringRedisTemplate")
//	private ValueOperations<String, String> valueOperationsString;
    //	@Resource(name = "redisTemplate")
//	private ValueOperations<Object, Object> valueOperations;

    @Autowired
    public PersonDao(StringRedisTemplate stringRedisTemplate, RedisTemplate<Object, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

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
