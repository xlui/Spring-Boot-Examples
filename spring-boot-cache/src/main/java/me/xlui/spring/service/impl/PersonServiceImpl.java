package me.xlui.spring.service.impl;

import me.xlui.spring.entity.Person;
import me.xlui.spring.repo.PersonRepository;
import me.xlui.spring.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // 如果没有指定 key，则方法参数作为 key 保存到缓存中

    @Override
    @CachePut(value = "people", key = "#person.id")
    // CachePut 缓存新增的或者更新的数据到缓存，其中缓存名为 people，数据的 key 为 person 的 id
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为 id、key 为：" + p.getId() + "数据做了缓存");
        return p;
    }

    @Override
    @CacheEvict(value = "people", key = "#id")
    // CacheEvict 从缓存 people 中删除 key 为 id 的数据
    public void remove(Long id) {
        System.out.println("删除了id、key 为" + id + "的数据缓存");
        personRepository.delete(id);
    }

    @Override
    @Cacheable(value = "people", key = "#person.id")
    // Cacheable 缓存 key 为 person 的 id 的数据到 people 中
    public Person find(Person person) {
        Person p = personRepository.findOne(person.getId());
        System.out.println("为 id、key 为：" + p.getId() + "数据做了缓存");
        return p;
    }
}
