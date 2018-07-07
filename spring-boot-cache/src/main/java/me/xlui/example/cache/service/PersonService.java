package me.xlui.example.cache.service;

import me.xlui.example.cache.entity.Person;

public interface PersonService {
    Person save(Person person);

    void remove(Integer id);

    Person find(Person person);
}
