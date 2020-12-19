package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> listAll();
    List<Student> searchByName(String text);
    Student save(String username, String password, String name, String surname);
    Optional<Student> checkCridentals(String username, String password);
    Student findByUsername(String username);
}
