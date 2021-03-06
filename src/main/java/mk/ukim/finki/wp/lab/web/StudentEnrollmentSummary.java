package mk.ukim.finki.wp.lab.web;

import mk.ukim.finki.wp.lab.model.Course;
import mk.ukim.finki.wp.lab.model.Grade;
import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.GradeService;
import mk.ukim.finki.wp.lab.service.StudentService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet (name = "student-enroll-summary-servlet", urlPatterns = "/StudentEnrollmentSummary")
public class StudentEnrollmentSummary extends HttpServlet {

    private final CourseService courseService;
    private final StudentService studentService;
    private final GradeService gradeService;
    private final SpringTemplateEngine springTemplateEngine;

    public StudentEnrollmentSummary(CourseService courseService,
                                    StudentService studentService,
                                    SpringTemplateEngine springTemplateEngine,
                                    GradeService gradeService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.springTemplateEngine = springTemplateEngine;
        this.gradeService = gradeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("courseID").toString() == null)
        {
            resp.sendRedirect("/listCourses");
            return;
        }
        String courseID = (String) req.getSession().getAttribute("courseID");
        Long cID = Long.parseLong(courseID);
        WebContext context = new WebContext(req,resp,req.getServletContext());
        List<Grade> grades = gradeService.findByCourseId(cID);
        context.setVariable("grades", grades);
        context.setVariable("students", courseService.listStudentsByCourse(cID));
        context.setVariable("course", courseService.findCourse(cID));
        springTemplateEngine.process("studentsInCourse.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String courseID = (String) req.getSession().getAttribute("courseID");
        Long cID = Long.parseLong(courseID);

        courseService.addStudentInCourse(username, cID);
        Student student = studentService.findByUsername(username);
        Course c = courseService.findCourse(cID).get();
        gradeService.save(student, c);

        resp.sendRedirect("/StudentEnrollmentSummary");

    }

}
