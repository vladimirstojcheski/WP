package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameAlreadyExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.FillAllFieldsException;
import mk.ukim.finki.wp.lab.repository.impl.CourseRepository;
import mk.ukim.finki.wp.lab.repository.impl.StudentRepository;
import mk.ukim.finki.wp.lab.repository.impl.TeacherRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaCourseRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaStudentRepository;
import mk.ukim.finki.wp.lab.repository.jpa.JpaTeacherRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private final JpaCourseRepository courseRepository;
    private final JpaStudentRepository studentRepository;
    private final JpaTeacherRepository teacherRepository;

    public CourseServiceImpl(JpaCourseRepository courseRepository, JpaStudentRepository studentRepository,
                             JpaTeacherRepository teacherRepository)
    {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Student> listStudentsByCourse(Long courseId) {
        Optional<Course> c = courseRepository.findById(courseId);
        return c.get().getStudents();
    }

    @Override
    @Transactional
    public Optional<Course> addStudentInCourse(String username, Long courseId) {
        Student student = this.studentRepository.findById(username).orElseThrow(
                () -> new CourseNameAlreadyExistsException(username));
        Optional<Course> course = this.courseRepository.findById(courseId);

        this.courseRepository.findById(courseId).get().getStudents().add(student);
        return course;

//        for(Student s : studentRepository.findAllStudents())
//        {
//            if (s.getUsername().equals(username))
//            {
//                return courseRepository.addStudentToCourse(s, courseRepository.findById(courseId));
//            }
//        }
//        Course c = courseRepository.findByCourseId(courseId);
//
//        return c;
    }

    @Override
    public List<Course> listAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findCourse(long courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Override
    public Course save(String name, String description, Long id, Type type) {
        Teacher teacher = teacherRepository.findById(id).get();
        Optional<Course> c = courseRepository.findAll()
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
        courseRepository.save(course);
        return course;
    }

    @Override
    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Course> edit(Long id, String name, String description, Long teacherId, Type type){
        Optional<Course> c = courseRepository.findAll()
                .stream().filter(s-> s.getName().equals(name)).findFirst();
        if(c.isPresent() && !c.get().getCourseId().equals(id))
        {
            throw new CourseNameAlreadyExistsException(name);
        }
        else if (name.equals("") || description.equals(""))
        {
            throw new FillAllFieldsException(name, description);
        }
        this.courseRepository.findById(id).get().setName(name);
        this.courseRepository.findById(id).get().setDescription(description);
        this.courseRepository.findById(id).get().setTeacher(this.teacherRepository.findById(teacherId).get());
        this.courseRepository.findById(id).get().setType(type);

        return this.courseRepository.findById(id);
    }


}
