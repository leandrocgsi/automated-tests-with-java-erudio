package br.com.erudio.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@WebMvcTest
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper mapper;
    
    @MockBean
    private PersonServices service;
    
    private Person person;
    
    @BeforeEach
    public void setup() {
        // Given / Arrange
        person = new Person(
            "Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil",
            "Male");
    }
    
    @Test
    @DisplayName("JUnit test for Given Person Object when Create Person then Return Saved Person")
    void testGivenPersonObject_WhenCreatePerson_thenReturnSavedPerson() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        given(service.create(any(Person.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
        
        // When / Act
        ResultActions response = mockMvc.perform(post("/person")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(person)));
        
        // Then / Assert
        response.andDo(print()).
            andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(person.getLastName())))
            .andExpect(jsonPath("$.email", is(person.getEmail())));
    }
    
    @Test
    @DisplayName("JUnit test for Given List of Persons when findAll Persons then Return Persons List")
    void testGivenListOfPersons_WhenFindAllPersons_thenReturnPersonsList() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male"));
        
        given(service.findAll()).willReturn(persons);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/person"));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()", is(persons.size())));
    }
    
    @Test
    @DisplayName("JUnit test for Given personId when findById then Return Person Object")
    void testGivenPersonId_WhenFindById_thenReturnPersonObject() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willReturn(person);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(person.getLastName())))
            .andExpect(jsonPath("$.email", is(person.getEmail())));
    }
    
    @Test
    @DisplayName("JUnit test for Given Invalid PersonId when findById then Return Not Found")
    void testGivenInvalidPersonId_WhenFindById_thenReturnNotFound() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);
        
        // When / Act
        ResultActions response = mockMvc.perform(get("/person/{id}", personId));
        
        // Then / Assert
        response.andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    @DisplayName("JUnit test for Given Updated Person when Update then Return Updated Person Object")
    void testGivenUpdatedPerson_WhenUpdate_thenReturnUpdatedPersonObject() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willReturn(person);
        given(service.update(any(Person.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
        
        // When / Act
        Person updatedPerson = new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
        
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));
        
        // Then / Assert
        response.
            andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.firstName", is(updatedPerson.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(updatedPerson.getLastName())))
            .andExpect(jsonPath("$.email", is(updatedPerson.getEmail())));
    }
    
    @Test
    @DisplayName("JUnit test for Given Unexistent Person when Update then Return Not Found")
    void testGivenUnexistentPerson_WhenUpdate_thenReturnNotFound() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long personId = 1L;
        given(service.findById(personId)).willThrow(ResourceNotFoundException.class);
        given(service.update(any(Person.class)))
            .willAnswer((invocation) -> invocation.getArgument(1));
        
        // When / Act
        Person updatedPerson = new Person(
                "Leonardo",
                "Costa",
                "leonardo@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Male");
        
        ResultActions response = mockMvc.perform(put("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedPerson)));
        
        // Then / Assert
        response.
            andExpect(status().isNotFound())
            .andDo(print());
    }
    
    @Test
    @DisplayName("JUnit test for Given personId when Delete then Return NotContent")
    void testGivenPersonId_WhenDelete_thenReturnNotContent() throws JsonProcessingException, Exception {
        
        // Given / Arrange
        long personId = 1L;
        willDoNothing().given(service).delete(personId);
        
        // When / Act
        ResultActions response = mockMvc.perform(delete("/person/{id}", personId));
        
        // Then / Assert
        response.
            andExpect(status().isNoContent())
                .andDo(print());
    }
}