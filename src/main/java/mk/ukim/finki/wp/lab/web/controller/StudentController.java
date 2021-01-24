package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;

    public StudentController(StudentService studentService,
                             CourseService courseService,
                             GradeService gradeService)
    {
        this.studentService = studentService;
        this.courseService = courseService;
        this.gradeService = gradeService;
    }

    @GetMapping
    public String getStudentsPage(Model model)
    {
        model.addAttribute("students", studentService.listAll());
        model.addAttribute("bodyContent", "listStudents");
        return "master-template";
    }

    @GetMapping("/create-student")
    public String getCreateStudentPage(Model model)
    {
        model.addAttribute("bodyContent", "createStudent");
        return "master-template";
    }

    @PostMapping("/create")
    public String saveStudent(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String name,
                              @RequestParam String surname)
    {
        studentService.save(username, password, name, surname);
        return "redirect:/students";
    }

    @PostMapping("/add-in-course")
    public String addStudentInCourse(@RequestParam String username,
                                     HttpSession session)
    {
        String courseID = (String) session.getAttribute("courseID");
        Long cID = Long.parseLong(courseID);

        courseService.addStudentInCourse(username, cID);
        Student student = studentService.findByUsername(username);
        Course c = courseService.findCourse(cID).get();
        gradeService.save(student, c);
        return "redirect:/students/enrollment";
    }

    @GetMapping("enrollment")
    public String getStudentEnrollmentPage(HttpSession session, Model model)
    {
        String courseID = (String) session.getAttribute("courseID");
        Long cID = Long.parseLong(courseID);
        model.addAttribute("grades", gradeService.findByCourseId(cID));
        model.addAttribute("students", courseService.listStudentsByCourse(cID));
        model.addAttribute("course", courseService.findCourse(cID));
        model.addAttribute("bodyContent", "studentsInCourse");
        return "master-template";
    }
}
