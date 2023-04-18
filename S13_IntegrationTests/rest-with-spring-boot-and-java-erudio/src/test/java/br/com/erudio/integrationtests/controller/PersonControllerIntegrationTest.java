package br.com.erudio.integrationtests.controller;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.config.TestConfigs;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.model.Person;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PersonControllerIntegrationTest extends AbstractIntegrationTest {
    
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Person person;
    
    @BeforeAll
    public static void setup() {
        
        // Given / Arrange
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        specification = new RequestSpecBuilder()
            .setBasePath("/person")
            .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
            .build();
        
        person = new Person(
            "Leandro",
            "Costa",
            "leandro@erudio.com.br",
            "Uberlândia - Minas Gerais - Brasil",
            "Male");
    }

    @Test
    @Order(1)
    @DisplayName("JUnit integration given Person Object when Create one Person should Return a Person Object")
    void integrationTestGivenPersonObject_when_CreateOnePerson_ShouldReturnAPersonObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(person)
                .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        
        Person createdPerson = objectMapper.readValue(content, Person.class);
        
        person = createdPerson;
        
        assertNotNull(createdPerson);
        
        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());
        assertNotNull(createdPerson.getEmail());
        
        assertTrue(createdPerson.getId() > 0);
        assertEquals("Leandro", createdPerson.getFirstName());
        assertEquals("Costa", createdPerson.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertEquals("leandro@erudio.com.br", createdPerson.getEmail());
    }
    
    @Test
    @Order(2)
    @DisplayName("JUnit integration given Person Object when Update one Person should Return a Updated Person Object")
    void integrationTestGivenPersonObject_when_UpdateOnePerson_ShouldReturnAUpdatedPersonObject() throws JsonMappingException, JsonProcessingException {
        
        person.setFirstName("Leonardo");
        person.setEmail("leonardo@erudio.com.br");
        
        var content = given().spec(specification)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(person)
                .when()
                    .put()
                .then()
                    .statusCode(200)
                        .extract()
                            .body()
                                .asString();
        
        Person updatedPerson = objectMapper.readValue(content, Person.class);
        
        person = updatedPerson;
        
        assertNotNull(updatedPerson);
        
        assertNotNull(updatedPerson.getId());
        assertNotNull(updatedPerson.getFirstName());
        assertNotNull(updatedPerson.getLastName());
        assertNotNull(updatedPerson.getAddress());
        assertNotNull(updatedPerson.getGender());
        assertNotNull(updatedPerson.getEmail());
        
        assertTrue(updatedPerson.getId() > 0);
        assertEquals("Leonardo", updatedPerson.getFirstName());
        assertEquals("Costa", updatedPerson.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", updatedPerson.getAddress());
        assertEquals("Male", updatedPerson.getGender());
        assertEquals("leonardo@erudio.com.br", updatedPerson.getEmail());
    }
    
    @Test
    @Order(3)
    @DisplayName("JUnit integration given Person Object when findById should Return a Person Object")
    void integrationTestGivenPersonObject_when_findById_ShouldReturnAPersonObject() throws JsonMappingException, JsonProcessingException {
        
        var content = given().spec(specification)
                .pathParam("id", person.getId())
            .when()
                .get("{id}")
            .then()
                .statusCode(200)
                    .extract()
                        .body()
                            .asString();
        
        Person foundPerson = objectMapper.readValue(content, Person.class);
        
        assertNotNull(foundPerson);
        
        assertNotNull(foundPerson.getId());
        assertNotNull(foundPerson.getFirstName());
        assertNotNull(foundPerson.getLastName());
        assertNotNull(foundPerson.getAddress());
        assertNotNull(foundPerson.getGender());
        assertNotNull(foundPerson.getEmail());
        
        assertTrue(foundPerson.getId() > 0);
        assertEquals("Leonardo", foundPerson.getFirstName());
        assertEquals("Costa", foundPerson.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", foundPerson.getAddress());
        assertEquals("Male", foundPerson.getGender());
        assertEquals("leonardo@erudio.com.br", foundPerson.getEmail());
    }
    
    @Test
    @Order(4)
    @DisplayName("JUnit integration given Person Object when findAll should Return a Persons List")
    void integrationTest_when_findAll_ShouldReturnAPersonsList() throws JsonMappingException, JsonProcessingException {
        
        Person anotherPerson = new Person(
                "Gabriela",
                "Rodriguez",
                "gabi@erudio.com.br",
                "Uberlândia - Minas Gerais - Brasil",
                "Female"
            );
        
        given().spec(specification)
            .contentType(TestConfigs.CONTENT_TYPE_JSON)
            .body(anotherPerson)
        .when()
            .post()
        .then()
            .statusCode(200);
        
        var content = given().spec(specification)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        
        Person[] myArray = objectMapper.readValue(content, Person[].class);
        List<Person> people = Arrays.asList(myArray);
        
        Person foundPersonOne = people.get(0);
        
        assertNotNull(foundPersonOne);
        
        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertNotNull(foundPersonOne.getEmail());
        
        assertTrue(foundPersonOne.getId() > 0);
        assertEquals("Leonardo", foundPersonOne.getFirstName());
        assertEquals("Costa", foundPersonOne.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());
        assertEquals("leonardo@erudio.com.br", foundPersonOne.getEmail());
        
        Person foundPersonTwo = people.get(1);
        
        assertNotNull(foundPersonTwo);
        
        assertNotNull(foundPersonTwo.getId());
        assertNotNull(foundPersonTwo.getFirstName());
        assertNotNull(foundPersonTwo.getLastName());
        assertNotNull(foundPersonTwo.getAddress());
        assertNotNull(foundPersonTwo.getGender());
        assertNotNull(foundPersonTwo.getEmail());
        
        assertTrue(foundPersonTwo.getId() > 0);
        assertEquals("Gabriela", foundPersonTwo.getFirstName());
        assertEquals("Rodriguez", foundPersonTwo.getLastName());
        assertEquals("Uberlândia - Minas Gerais - Brasil", foundPersonTwo.getAddress());
        assertEquals("Female", foundPersonTwo.getGender());
        assertEquals("gabi@erudio.com.br", foundPersonTwo.getEmail());
    }
    
    @Test
    @Order(5)
    @DisplayName("JUnit integration given Person Object when delete should Return No Content")
    void integrationTestGivenPersonObject_when_delete_ShouldReturnNoContent() throws JsonMappingException, JsonProcessingException {
        
        given().spec(specification)
                .pathParam("id", person.getId())
            .when()
                .delete("{id}")
            .then()
                .statusCode(204);
    }
}
