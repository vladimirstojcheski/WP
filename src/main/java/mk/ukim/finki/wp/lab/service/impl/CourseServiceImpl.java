package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameAlreadyExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.FillAllFieldsException;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.repository.StudentRepository;
import mk.ukim.finki.wp.lab.repository.TeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
                             TeacherRepository teacherRepository)
    {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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

    @Override
    public Course save(String name, String description, Long id, Type type) {
        Teacher teacher = teacherRepository.findById(id).get();
        Optional<Course> c = courseRepository.findAllCourses()
                .stream().filter(s-> s.getName().equals(name)).findFirst();
        if(c.isPresent())
        {
            throw new CourseNameAlreadyExistsException(name);
        }
        else if (name.equals("") || description.equals(""))
        {
            throw new FillAllFieldsException(name, description);
        }
        Course course = new Course(name, description, teacher, type);
            courseRepository.addCourse(course);
            return course;

        }

    @Override
    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }

    @Override
    public Course edit(Long id, String name, String description, Long teacherId, Type type){
        Optional<Course> c = courseRepository.findAllCourses()
                .stream().filter(s-> s.getName().equals(name)).findFirst();
        if(c.isPresent() == true && !c.get().getCourseId().equals(id))
        {
            throw new CourseNameAlreadyExistsException(name);
        }
        else if (name.equals("") || description.equals(""))
        {
            throw new FillAllFieldsException(name, description);
        }
        this.courseRepository.findById(id).setName(name);
        this.courseRepository.findById(id).setDescription(description);
        this.courseRepository.findById(id).setTeacher(this.teacherRepository.findById(teacherId).get());
        this.courseRepository.findById(id).setType(type);

        return this.courseRepository.findById(id);
    }


}
