package me.xlui.example.service;

import me.xlui.example.entity.Person;

public interface PersonService {
    Person save(Person person);

    void remove(Integer id);

    Person find(Person person);
}
