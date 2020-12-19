package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCourseRepository extends JpaRepository<Course, Long> {
    void deleteById(Long id);

    Optional<Course> findByCourseId(Long id);

    @Query(value = "SELECT c.students from Course c where c.type = 'MANDATORY'")
    List<Student> findStudentsWithMandatoryCourse();

    List<Course> findByTeacher(Teacher t);

    List<Student> findByCourseIdAndTeacher(Long id, Teacher t);

}
