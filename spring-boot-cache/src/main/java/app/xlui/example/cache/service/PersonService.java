package app.xlui.example.cache.service;

import app.xlui.example.cache.entity.Person;

public interface PersonService {
    Person save(Person person);

    void remove(Integer id);

    Person find(Person person);
}
