package br.com.erudio.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.erudio.model.Person;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;
    
    @DisplayName("Given Person Object when Save then Return Saved Person")
    @Test
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {
        
        // Given / Arrange
        Person person0 = new Person("Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil", "Male");
        
        // When / Act
        Person savedPerson = repository.save(person0);
        
        // Then / Assert
        assertNotNull(savedPerson);
        assertTrue(savedPerson.getId() > 0);
    }
    
    @DisplayName("Given Person List when findAll then Return Person List")
    @Test
    void testGivenPersonList_whenFindAll_thenReturnPersonList() {
        
        // Given / Arrange
        Person person0 = new Person("Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil", "Male");
        
        Person person1 = new Person("Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil", "Male");
        
        repository.save(person0);
        repository.save(person1);
        
        // When / Act
        List<Person> personList = repository.findAll();
        
        // Then / Assert
        assertNotNull(personList);
        assertEquals(2, personList.size());
    }
    
    @DisplayName("Given Person Object when findByID then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByID_thenReturnPersonObject() {
        
        // Given / Arrange
        Person person0 = new Person("Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil", "Male");
        
        repository.save(person0);
        
        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();
        
        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }
    
    @DisplayName("Given Person Object when findByEmail then Return Person Object")
    @Test
    void testGivenPersonObject_whenFindByEmail_thenReturnPersonObject() {
        
        // Given / Arrange
        Person person0 = new Person("Leandro",
                "Costa",
                "leandro@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil", "Male");
        
        repository.save(person0);
        
        // When / Act
        Person savedPerson = repository.findByEmail(person0.getEmail()).get();
        
        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals(person0.getId(), savedPerson.getId());
    }
    
    @DisplayName("Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_whenUpdatePerson_thenReturnUpdatedPersonObject() {
        
        // Given / Arrange
        Person person0 = new Person("Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil", "Male");
        
        repository.save(person0);
        
        // When / Act
        Person savedPerson = repository.findById(person0.getId()).get();
        savedPerson.setFirstName("Leonardo");
        savedPerson.setEmail("leonardo@erudio.com.br");
        
        Person updatedPerson = repository.save(savedPerson);
        
        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());
    }
    
    @DisplayName("Given Person Object when Delete then Remove Person")
    @Test
    void testGivenPersonObject_whenDelete_thenRemovePerson() {
        
        // Given / Arrange
        Person person0 = new Person("Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil", "Male");
        
        repository.save(person0);
        
        // When / Act
        repository.deleteById(person0.getId());
        
        Optional<Person> personOptional = repository.findById(person0.getId());
        
        // Then / Assert
        assertTrue(personOptional.isEmpty());
    }
}
