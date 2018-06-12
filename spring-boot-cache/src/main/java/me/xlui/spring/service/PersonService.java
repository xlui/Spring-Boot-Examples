package me.xlui.spring.service;

import me.xlui.spring.entity.Person;

public interface PersonService {
    Person save(Person person);

    void remove(Integer id);

    Person find(Person person);
}
