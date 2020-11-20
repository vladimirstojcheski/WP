package mk.ukim.finki.wp.lab.model;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;
    private Type type;

    public Course(String name, String description, Teacher teacher, Type type)
    {
        this.courseId = (long) (Math.random()*1000);
        this.name = name;
        this.description = description;
        this.students = new ArrayList<>();
        this.teacher = teacher;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
