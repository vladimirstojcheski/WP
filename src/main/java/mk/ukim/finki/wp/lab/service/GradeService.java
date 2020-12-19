package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;

import java.util.List;

public interface GradeService {
    List<Grade> findByCourseId(Long id);
    List<Grade> listAll();
    Grade save (Student student, Course course);
}
