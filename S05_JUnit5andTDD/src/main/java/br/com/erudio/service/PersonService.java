package br.com.erudio.service;

import br.com.erudio.model.Person;

public class PersonService implements IPersonService {

    @Override
    public Person createPerson(Person person) {
        return person;
    }

}
