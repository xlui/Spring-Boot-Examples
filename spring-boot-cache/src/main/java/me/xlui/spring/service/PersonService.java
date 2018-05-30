package me.xlui.spring.service;

import me.xlui.spring.entity.Person;

public interface PersonService {
    Person save(Person person);

    void remove(Long id);

    Person find(Person person);
}
