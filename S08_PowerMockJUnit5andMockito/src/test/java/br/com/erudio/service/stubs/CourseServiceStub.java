package br.com.erudio.service.stubs;

import java.util.Arrays;
import java.util.List;

import br.com.erudio.service.CourseService;

public class CourseServiceStub implements CourseService{

    public List<String> retrieveCourses(String student) {
        return Arrays.asList(
                "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "Spotify Engineering Culture Desmistificado",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
                "Docker do Zero à Maestria - Contêinerização Desmistificada",
                "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
                "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
                "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
                "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
                "Microsserviços do 0 com Spring Cloud, Kotlin e Docker"
            );
    }

    @Override
    public List<String> doSomething(String student) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteCourse(String course) {
        // TODO Auto-generated method stub
    }

}
