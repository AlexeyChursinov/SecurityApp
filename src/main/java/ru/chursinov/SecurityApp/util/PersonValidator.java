package ru.chursinov.SecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.chursinov.SecurityApp.models.Person;
import ru.chursinov.SecurityApp.services.PeopleService;
import ru.chursinov.SecurityApp.services.PersonDetailsService;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService, PeopleService peopleService) {
        this.personDetailsService = personDetailsService;
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (peopleService.loadUserByUsername(person.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
        } else {
            return;
        }

    }
}
