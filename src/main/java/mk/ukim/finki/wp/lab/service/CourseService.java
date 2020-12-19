package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Type;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Student> listStudentsByCourse(Long courseId);
    Optional<Course> addStudentInCourse(String username, Long courseId);
    List<Course> listAll();
    Optional<Course> findCourse(long courseId);
    Course save(String name, String description, Long id, Type type);
    void deleteById(Long id);
    Optional<Course> edit(Long id, String name, String description, Long teacherId, Type type);
}
