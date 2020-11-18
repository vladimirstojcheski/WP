package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("courses")
public class CourseController {

    private final CourseService courseService;
    private final TeacherService teacherService;


    public CourseController(CourseService courseService, TeacherService teacherService)
    {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String getCoursesPage(@RequestParam(required = false) String error, Model model)
    {
        List<Course> courses = this.courseService.listAll();
        model.addAttribute("courses", courses);
        return "listCourses";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(Model model)
    {
        List<Teacher> teachers = this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "add-course";
    }



    
}
