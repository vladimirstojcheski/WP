package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository)
    {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        return courseRepository.findAllStudentsByCourse(courseId);
    }

    @Override
    public Course addStudentInCourse(String username, Long courseId) {
        for(Student s : studentRepository.findAllStudents())
        {
            if (s.getUsername().equals(username))
            {
                return courseRepository.addStudentToCourse(s, courseRepository.findById(courseId));
            }
        }
//        List<Student> sl = studentRepository.findAllStudents().stream()
//                .filter(r-> r.getUsername().equals(username)).collect(Collectors.toList());
//        Student s = sl.get(0);
        Course c = courseRepository.findById(courseId);
//        courseRepository.addStudentToCourse(s, c);
        return c;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAllCourses();
    }

    @Override
    public Course findCourse(long courseId) {
        return courseRepository.findById(courseId);
    }


}
