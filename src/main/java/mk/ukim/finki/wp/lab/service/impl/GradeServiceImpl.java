package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.jpa.JpaCourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaGradeRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaStudentRepository;
import mk.ukim.finki.wp.lab.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {

    private final JpaCourseRepository courseRepository;
    private final JpaStudentRepository studentRepository;
    private final JpaGradeRepository gradeRepository;

    public GradeServiceImpl(JpaCourseRepository courseRepository,
                            JpaStudentRepository studentRepository,
                            JpaGradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    @Override
    public List<Grade> findByCourseId(Long id) {
        return gradeRepository.findAllByCourse(courseRepository.findById(id).get());
    }

    @Override
    public List<Grade> listAll() {
        return gradeRepository.findAll();
    }

    @Override
    public Grade save(Student student, Course course) {
        return gradeRepository.save(new Grade(student, course));
    }
}
