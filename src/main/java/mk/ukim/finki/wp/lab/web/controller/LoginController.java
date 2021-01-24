package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final StudentService studentService;


    public LoginController(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getLoginPage(Model model)
    {
        model.addAttribute("bodyContent","login");
        return "master-template";

    }

    @PostMapping
    public String login(HttpServletRequest req, Model model)
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (!studentService.checkCridentals(username, password).isEmpty())
        {
            req.getSession().setAttribute("username", username);
            String name="";
            String lastName="";
            for(Student s : studentService.listAll()) {
                if (s.getUsername().equals(username)) {
                    name = s.getName();
                    lastName = s.getSurname();
                }
            }
            req.getSession().setAttribute("name", name);
            req.getSession().setAttribute("lastName", lastName);
            req.getSession().removeAttribute("hasError");
            req.getSession().removeAttribute("error");
            return "redirect:/courses";
        }
        else
        {
            req.getSession().setAttribute("hasError", true);
            req.getSession().setAttribute("error", "Invalid user credentials");
            return "redirect:/login";
        }
    }
}
