package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Teacher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TeacherRepository {

    private List<Teacher> teachers;

    public TeacherRepository()
    {
        this.teachers = new ArrayList<>();

        this.teachers.add(new Teacher("Riste", "Stojanov"));
        this.teachers.add(new Teacher("Dimitar", "Trajanov"));
        this.teachers.add(new Teacher("Gjorgi", "Madzarov"));
        this.teachers.add(new Teacher("Kire", "Trivodaliev"));
        this.teachers.add(new Teacher("Dejan", "Gorgevik"));
    }
    public List<Teacher> findAll()
    {
        return this.teachers;
    }

    public Optional<Teacher> findById(Long id)
    {
        return this.teachers
                .stream()
                .filter(i -> i.getId().equals(id)).findFirst();
    }

    public String getNameSurname(Long id)
    {
        return String.format("%s %s", this.findById(id).get().getName(),
                this.findById(id).get().getSurname());
    }
}
