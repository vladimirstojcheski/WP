package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository{

    private List<Course> courses;

    public CourseRepository()
    {
        List<Student> students = new ArrayList<>();
        this.courses = new ArrayList<>();
        students.add(new Student("vladimir.stojceski", "password", "Vladimir", "Stojceski"));
        students.add(new Student("david.laskoski", "password2", "David", "Laskoski"));
        students.add(new Student("viktor.nikoloski", "password3", "Viktor", "Nikoloski"));
        students.add(new Student("ljupce.trajkoski", "password4", "Ljupce", "Trajkoski"));
        students.add(new Student("petko.petkoski", "password5", "Petko", "Petkoski"));
        this.courses.add(new Course("Web programming", "web sesc", new ArrayList<Student>()));
        this.courses.add(new Course("Operating systems", "OS desc", new ArrayList<Student>()));
        this.courses.add(new Course("Artificial Intelligence", "AI desc", new ArrayList<Student>()));
        this.courses.add(new Course("Software engineering", "SI desc", new ArrayList<Student>()));
        this.courses.add(new Course("Advanced programming", "AP desc", new ArrayList<Student>()));
    }

    public List<Course> findAllCourses()
    {
        return this.courses;
    }

    public Course findById(Long courseId)
    {
        for(Course c : this.courses)
        {
            if (c.getCourseId().equals(courseId))
            {
                return c;
            }
        }
        return null;
    }

    public List<Student> findAllStudentsByCourse(Long courseId)
    {
        for (Course c : this.courses)
        {
            if(c.getCourseId().equals(courseId))
            {
                return c.getStudents();
            }
        }
        return null;
    }

    public Course addStudentToCourse(Student student, Course course)
    {

        course.getStudents().add(student);

//        for(Course c : this.courses)
//        {
//            if(c.getCourseId() == course.getCourseId())
//            {
//                c.getStudents().add(student);
//                return c;
//            }
//        }
        return course;
    }
}
