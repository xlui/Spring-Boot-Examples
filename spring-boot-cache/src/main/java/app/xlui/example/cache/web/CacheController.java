package app.xlui.example.cache.web;

import app.xlui.example.cache.service.PersonService;
import app.xlui.example.cache.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController {
    private final PersonService personService;

    @Autowired
    public CacheController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/put")
    public Person put(Person person) {
        return personService.save(person);
    }

    @RequestMapping("/able")
    public Person cacheable(Person person) {
        return personService.find(person);
    }

    @RequestMapping("/evit")
    public String evit(Integer id) {
        personService.remove(id);
        return "ok";
    }
}
