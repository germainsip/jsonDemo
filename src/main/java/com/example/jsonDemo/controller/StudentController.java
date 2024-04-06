package com.example.jsonDemo.controller;

import com.example.jsonDemo.model.Post;
import com.example.jsonDemo.model.Student;
import com.example.jsonDemo.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<Student> read() {
        return service.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> read(@PathVariable(name = "id") Long id) {
        Student foundStudent = service.read(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundStudent);
        }
    }

    @GetMapping("/tomoe")
    public List<Post> getTomoe(){
        String uri = "https://tomoetek.fr/book/api/posts";
        RestTemplate restTemplate = new RestTemplate();
        List<Post> posts = new ArrayList<>(List.of(restTemplate.getForEntity(uri, Post[].class).getBody()));
        posts.add(new Post(12L,"test"));
        return posts;
    }
}
