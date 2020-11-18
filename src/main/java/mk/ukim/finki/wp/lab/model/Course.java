package mk.ukim.finki.wp.lab.model;

import java.util.List;

public class Course {

    private Long courseId;
    private String name;
    private String description;
    private List<Student> students;
    private Teacher teacher;

    public Course(String name, String description, List<Student> students)
    {
        this.courseId = (long) (Math.random()*1000);
        this.name = name;
        this.description = description;
        this.students = students;
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
