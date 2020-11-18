package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.repository.CourseRepository;
import mk.ukim.finki.wp.lab.service.CourseService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet (name = "courses-servlet", urlPatterns = "/listCourses")
public class CoursesListServlet extends HttpServlet {

    private final CourseService courseService;
    private final SpringTemplateEngine springTemplateEngine;

    public CoursesListServlet (CourseService courseService, SpringTemplateEngine springTemplateEngine)
    {
        this.courseService = courseService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req,resp,req.getServletContext());
        context.setVariable("courses", courseService.listAll());
        this.springTemplateEngine.process("listCourses.html", context,resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("courseID") == null)
        {
            resp.sendRedirect("/listCourses");
            return;
        }
        String courseID = req.getParameter("courseID");
        req.getSession().setAttribute("courseID", courseID);

        resp.sendRedirect("/AddStudent");
    }
}
