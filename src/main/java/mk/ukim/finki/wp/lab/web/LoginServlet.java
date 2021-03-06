package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (name = "login-servlet", urlPatterns = "/servlet/login")
public class LoginServlet extends HttpServlet {

    private final StudentService studentService;
    private final SpringTemplateEngine springTemplateEngine;

    public LoginServlet(StudentService studentService, SpringTemplateEngine springTemplateEngine) {
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());
        springTemplateEngine.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            resp.sendRedirect("/courses");
        }
        else
        {
            req.getSession().setAttribute("hasError", true);
            req.getSession().setAttribute("error", "Invalid user credentials");
            resp.sendRedirect("/login");
            return;
        }

    }
}
