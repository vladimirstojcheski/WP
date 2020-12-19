package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaStudentRepository extends JpaRepository<Student, String> {
    List<Student> findAll();
    List<Student> findAllByName(String text);
    Optional<Student> findByUsernameAndPassword(String username, String password);
}
