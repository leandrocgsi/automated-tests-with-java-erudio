package br.com.erudio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.erudio.model.Person;
import br.com.erudio.service.IPersonService;
import br.com.erudio.service.PersonService;

public class PersonServiceTest {

    @DisplayName("When Create a Person with Success Should Return a Person Object")
    @Test
    void testCreatePerson_WhenSucess_ShouldReturnPersonObject() {
        
        // Given / Arrange
        IPersonService service = new PersonService();
        
        Person person = new Person(
                "Keith",
                "Moon",
                "kmoon@erudio.com.br",
                "Wembley - UK",
                "Male"
            );
        
        // When / Act
        Person actual = service.createPerson(person);
        
        // Then / Assert
        assertNotNull(actual, () -> "The createPerson() should not have returned null!");
    }
    
    @DisplayName("When Create a Person with Success Should Contains FirstName in Returned Person Object")
    @Test
    void testCreatePerson_WhenSucess_ShouldContainsFirstNameInReturnedPersonObject() {
        
        // Given / Arrange
        IPersonService service = new PersonService();
        
        Person person = new Person(
                "Keith",
                "Moon",
                "kmoon@erudio.com.br",
                "Wembley - UK",
                "Male"
                );
        
        // When / Act
        Person actual = service.createPerson(person);
        
        // Then / Assert
        assertEquals(person.getFirstName(), actual.getFirstName(), () -> "The FistName is Different!");
    }
}