package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.model.exceptions.CourseNameAlreadyExistsException;
import mk.ukim.finki.wp.lab.model.exceptions.FillAllFieldsException;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String getCoursesPage(@RequestParam(required = false) String error, Model model, HttpSession session)
    {
        List<Course> courses = null;
        if(error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if(session.getAttribute("type")!=null)
        {
            courses = this.courseService.listAll()
                    .stream().filter(x->x.getType().equals(session.getAttribute("type")))
                    .sorted(Comparator.comparing(Course::getName))
                    .collect(Collectors.toList());
        }
        else {
            courses = this.courseService.listAll()
                    .stream().sorted((Comparator.comparing(Course::getName)))
                    .collect(Collectors.toList());
        }

        List<Type> types = Arrays.asList(Type.values());
        model.addAttribute("types", types);
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "listCourses");
        return "master-template";
}

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddCoursePage(@RequestParam(required = false) String error, Model model)
    {
        if(error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Teacher> teachers = this.teacherService.findAll();
        List<Type> types = Arrays.asList(Type.values());
        model.addAttribute("types", types);
        model.addAttribute("teachers", teachers);
        model.addAttribute("bodyContent", "add-course");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveCourse(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long teacherId,
            @RequestParam Type type,
            HttpSession session)
    {
        if(session.getAttribute("id") != null)
        {
            Long id = (long) session.getAttribute("id");
            try {
                this.courseService.edit(id, name, description, teacherId, type);
                session.removeAttribute("id");
                session.removeAttribute("type");
                return "redirect:/courses";
            }
            catch (FillAllFieldsException | CourseNameAlreadyExistsException ex)
            {
                return "redirect:/courses/edit-form/" + id + "?error=" + ex.getMessage();
            }
        }
        try {
            this.courseService.save(name, description, teacherId, type);
            session.removeAttribute("type");
        }
        catch (FillAllFieldsException | CourseNameAlreadyExistsException ex)
        {
            return "redirect:/courses/add-form?error=" + ex.getMessage();
        }
        return "redirect:/courses";

    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditCoursePage(@PathVariable Long id,
                                    @RequestParam(required = false) String error,
                                    Model model, HttpSession session)
    {
        if(error != null && !error.isEmpty())
        {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if(!this.courseService.findCourse(id).isEmpty()) {
            session.setAttribute("id", id);
            Optional<Course> course = this.courseService.findCourse(id);
            List<Teacher> teachers = this.teacherService.findAll();
            List<Type> types = Arrays.asList(Type.values());
            model.addAttribute("types", types);
            model.addAttribute("c", course);
            model.addAttribute("teachers", teachers);
            model.addAttribute("bodyContent", "add-course");
            return "master-template";
        }
        return "redirect:/courses?error=CourseNotFound";
    }

    @PostMapping("/filter")
    public String getFilteredCoursePage(@RequestParam Type type, HttpSession session)
    {
        session.setAttribute("type", type);
        return "redirect:/courses";
    }

    @PostMapping("/reset")
    public String getResetedCoursePage(HttpSession session)
    {
            session.removeAttribute("type");
            return "redirect:/courses";
    }

    @PostMapping("/select-course")
    public String getStudentsPage(@RequestParam String courseID, HttpSession session)
    {
        session.setAttribute("courseID", courseID);
        session.removeAttribute("type");
        return "redirect:/students";
    }

    @PostMapping("select-course-user")
    public String getStudentEnrollmentPage(@RequestParam String courseID, HttpSession session)
    {
        session.setAttribute("courseID", courseID);
        session.removeAttribute("type");
        return "redirect:/students/enrollment";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id)
    {
        this.courseService.deleteById(id);
        return "redirect:/courses";
    }
}
