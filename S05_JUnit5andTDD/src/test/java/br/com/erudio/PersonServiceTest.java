package br.com.erudio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.erudio.model.Person;
import br.com.erudio.service.IPersonService;
import br.com.erudio.service.PersonService;

public class PersonServiceTest {

    IPersonService service;
    Person person;
    
    @BeforeEach
    void setup() {
        service = new PersonService();
        person = new Person(
            "Keith",
            "Moon",
            "kmoon@erudio.com.br",
            "Wembley - UK",
            "Male"
        );
    }
    
    @DisplayName("When Create a Person with Success Should Return a Person Object")
    @Test
    void testCreatePerson_WhenSucess_ShouldReturnPersonObject() {
        
        // Given / Arrange
        
        // When / Act
        Person actual = service.createPerson(person);
        
        // Then / Assert
        assertNotNull(actual, () -> "The createPerson() should not have returned null!");
    }
    
    @DisplayName("When Create a Person with Success Should Contains Valid Fields in Returned Person Object")
    @Test
    void testCreatePerson_WhenSucess_ShouldContainsValidFieldsInReturnedPersonObject() {
        
        // Given / Arrange
        
        // When / Act
        Person actual = service.createPerson(person);
        
        // Then / Assert
        assertNotNull(person.getId(), () -> "Person ID is Missing");
        assertEquals(
                person.getFirstName(),
                actual.getFirstName(),
                () -> "The Person FistName is Incorrect!");
        assertEquals(
                person.getLastName(),
                actual.getLastName(),
                () -> "The Person LastName is Incorrect!");
        assertEquals(
                person.getAddress(),
                actual.getAddress(),
                () -> "The Person Address is Incorrect!");
        assertEquals(
                person.getGender(),
                actual.getGender(),
                () -> "The Person Gender is Incorrect!");
        assertEquals(
                person.getEmail(),
                actual.getEmail(),
                () -> "The Person Email is Incorrect!");
    }
    
    @DisplayName("When Create a Person with null e-Mail Should throw Exception")
    @Test
    void testCreatePerson_WhithNullEMail_ShouldThrowIllegalArgumentException() {
        // Given / Arrange
        person.setEmail(null);
        
        var expectedMessage = "The Person e-Mail is null or empty!";
        
        // When / Act & Then / Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.createPerson(person),
            () -> "Empty e-Mail should have cause an IllegalArgumentException!"
        );
        
        // Then / Assert
        assertEquals(
            expectedMessage,
            exception.getMessage(),
            () -> "Exception error message is incorrect!");
    }
}