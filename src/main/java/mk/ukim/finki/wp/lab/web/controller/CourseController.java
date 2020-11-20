package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameAlreadyExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.FillAllFieldsException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
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
        if(error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = this.courseService.listAll();
        model.addAttribute("courses", courses);
        return "listCourses";
    }

    @GetMapping("/add-form")
    public String getAddCoursePage(@RequestParam(required = false) String error, Model model)
    {
        if(error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Teacher> teachers = this.teacherService.findAll();
        model.addAttribute("teachers", teachers);
        return "add-course";
    }

    @PostMapping("/add")
    public String saveCourse(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long teacherId,
            HttpSession session)
    {
        if(session.getAttribute("id") != null)
        {
            Long id = (long) session.getAttribute("id");
            try {
                this.courseService.edit(id, name, description, teacherId);
                session.removeAttribute("id");
                return "redirect:/courses";
            }
            catch (FillAllFieldsException | CourseNameAlreadyExistsException ex)
            {
                return "redirect:/courses/edit-form/" + id + "?error=" + ex.getMessage();
            }
        }
        try {
            this.courseService.save(name, description, teacherId);
        }
        catch (FillAllFieldsException | CourseNameAlreadyExistsException ex)
        {
            return "redirect:/courses/add-form?error=" + ex.getMessage();
        }
        return "redirect:/courses";

    }

    @GetMapping("/edit-form/{id}")
    public String getEditCoursePage(@PathVariable Long id,
                                    @RequestParam(required = false) String error,
                                    Model model, HttpSession session)
    {
        if(error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if(this.courseService.findCourse(id) != null) {
            session.setAttribute("id", id);
            Course course = this.courseService.findCourse(id);
            List<Teacher> teachers = this.teacherService.findAll();
            model.addAttribute("c", course);
            model.addAttribute("teachers", teachers);
            return "add-course";
        }
        return "redirect:/courses?error=CourseNotFound";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id)
    {
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }



    
}
