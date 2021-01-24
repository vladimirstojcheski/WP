package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.lab.repository.impl.StudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaStudentRepository;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final JpaStudentRepository studentRepository;

    public StudentServiceImpl(JpaStudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }


    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> searchByName(String text) {
        return studentRepository.findAllByName(text);
    }

    @Override
    public Student save(String username, String password, String name, String surname) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (this.studentRepository.findById(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        Student s = new Student(username,password,name,surname);
        this.studentRepository.save(s);
        return s;
    }

    @Override
    public Optional<Student> checkCridentals(String username, String password) {
        return studentRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findById(username).get();
    }


}
