package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    private List<Student> students;

    public StudentRepository()
    {
        this.students = new ArrayList<>();
        this.students.add(new Student("user1", "user1", "Vladimir", "Stojceski"));
        this.students.add(new Student("user2", "user2", "David", "Laskoski"));
        this.students.add(new Student("user3", "user3", "Viktor", "Nikoloski"));
        this.students.add(new Student("user4", "user4", "Ljupce", "Trajkoski"));
        this.students.add(new Student("user5", "user5", "Petko", "Petkoski"));
    }

    public List<Student> findAllStudents()
    {
        return this.students;
    }

    public List<Student> findAllByNameOrSurname(String text)
    {
        return this.students.stream()
                .filter(r->r.getName().contains(text) || r.getSurname().contains(text))
                .collect(Collectors.toList());
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public Student checkUserAndPass(String username, String password)
    {
        for(Student s : this.students)
        {
            if (s.getUsername().equals(username) && s.getPassword().equals(password))
            {
                return s;
            }
        }
        return null;
    }
}
