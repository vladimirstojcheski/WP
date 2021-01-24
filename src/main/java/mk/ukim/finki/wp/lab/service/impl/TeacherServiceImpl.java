package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.repository.impl.TeacherRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaTeacherRepository;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final JpaTeacherRepository teacherRepository;

    public TeacherServiceImpl (JpaTeacherRepository teacherRepository)
    {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return this.teacherRepository.findById(id);
    }

    @Override
    public Teacher create(String name, String surname) {
        Teacher t = new Teacher(name, surname);
        teacherRepository.save(t);
        return t;
    }
}
