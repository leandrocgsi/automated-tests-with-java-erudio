package br.com.erudio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {

    @Mock
    private PersonRepository repository;
    
    @InjectMocks
    private PersonServices services;

    private Person person0;
    
    @BeforeEach
    public void setup() {
        // Given / Arrange
        person0 = new Person(
            "Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil",
            "Male");
    }
    
    @DisplayName("JUnit test for Given Person Object when Save Person then Return Person Object")
    @Test
    void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {
        
        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.empty());
        given(repository.save(person0)).willReturn(person0);
        
        // When / Act
        Person savedPerson = services.create(person0);
        
        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());
    }   
    
    @DisplayName("JUnit test for Given Existing Email when Save Person then throws Exception")
    @Test
    void testGivenExistingEmail_WhenSavePerson_thenThrowsException() {
        
        // Given / Arrange
        given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));
        
        // When / Act
        assertThrows(ResourceNotFoundException.class, () -> {
            services.create(person0);
        });
        
        // Then / Assert
        verify(repository, never()).save(any(Person.class));
    }   
    
    @DisplayName("JUnit test for Given Persons List when findAll Persons then Return Persons List")
    @Test
    void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {
        
        // Given / Arrange
        Person person1 = new Person("Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
        
        given(repository.findAll()).willReturn(List.of(person0, person1));
        
        // When / Act
        List<Person> personsList = services.findAll();
        
        // Then / Assert
        assertNotNull(personsList);
        assertEquals(2, personsList.size());
    }   
    
    @DisplayName("JUnit test for Given Empty Persons List when findAll Persons then Return Empty Persons List")
    @Test
    void testGivenEmptyPersonsList_WhenFindAllPersons_thenReturnEmptyPersonsList() {
        
        // Given / Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());
        
        // When / Act
        List<Person> personsList = services.findAll();
        
        // Then / Assert
        assertTrue(personsList.isEmpty());
        assertEquals(0, personsList.size());
    }   
    
    @DisplayName("JUnit test for Given PersonId when findById then Return Person Object")
    @Test
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() {
        
        // Given / Arrange
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        
        // When / Act
        Person savedPerson = services.findById(1L);
        
        // Then / Assert
        assertNotNull(savedPerson);
        assertEquals("Leandro", savedPerson.getFirstName());
    }  
    
    @DisplayName("JUnit test for Given Person Object when Update Person then Return Updated Person Object")
    @Test
    void testGivenPersonObject_WhenUpdatePerson_thenReturnUpdatedPersonObject() {
        
        // Given / Arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        
        person0.setFirstName("Leonardo");
        person0.setEmail("leonardo@erudio.com.br");
        
        given(repository.save(person0)).willReturn(person0);
        
        // When / Act
        Person updatedPerson = services.update(person0);
        
        // Then / Assert
        assertNotNull(updatedPerson);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());
    }  
    
    @DisplayName("JUnit test for Given PersonID when Delete Person then do Nothing")
    @Test
    void testGivenPersonID_WhenDeletePerson_thenDoNothing() {
        
        // Given / Arrange
        person0.setId(1L);
        given(repository.findById(anyLong())).willReturn(Optional.of(person0));
        willDoNothing().given(repository).delete(person0);
        
        // When / Act
        services.delete(person0.getId());
        
        // Then / Assert
        verify(repository, times(1)).delete(person0);
    }  
}
