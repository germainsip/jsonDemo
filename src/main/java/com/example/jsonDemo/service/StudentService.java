package com.example.jsonDemo.service;

import com.example.jsonDemo.model.Student;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Map<Long, Student> repository = Arrays.stream(
            new Student[]{
             new Student(1, "Alan","Turing"),
                    new Student(2, "Sebastien","Bach"),
                    new Student(3, "Pablo","Picasso"),

            }
    ).collect(Collectors.toConcurrentMap(Student::getId, Function.identity()));

    private AtomicLong sequence = new AtomicLong();

    public List<Student> readAll() {
        return repository.values().stream().toList();
    }

    public Student read(Long id){
        return repository.get(id);
    }

    public Student create(Student student){
        long key = sequence.incrementAndGet();
        student.setId(key);
        repository.put(key,student);
        return student;
    }

    public Student update(Long id, Student student) {
        student.setId(id);
        Student oldStudent = repository.replace(id, student);
        return oldStudent == null ? null : student;
    }

    public void delete(Long id) {
        repository.remove(id);
    }

}
