package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByCourse(Course c);

    List<Grade> findByStudent(Student s);

    List<Student> findByGrade(Grade g);

}
