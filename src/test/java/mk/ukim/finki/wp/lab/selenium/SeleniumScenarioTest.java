package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Teacher;
import mk.ukim.finki.wp.lab.model.Type;
import mk.ukim.finki.wp.lab.service.CourseService;
import mk.ukim.finki.wp.lab.service.TeacherService;
import org.apache.catalina.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {


    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

//    @Autowired
//    CategoryService categoryService;
//
//
//    @Autowired
//    ProductService productService;


    private HtmlUnitDriver driver;

    private static Teacher t1;
    private static Teacher t2;
    private static User regularUser;
    private static User adminUser;

    private static String user = "user";
    private static String admin = "admin";

    private static boolean dataInitialized = false;


    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }


    private void initData() {
        if (!dataInitialized) {
            t1 = teacherService.create("teacher1","teacher1");
            t2 = teacherService.create("teacher2","teacher2");

//             regularUser = userService.register(user, user, user, user, user, Role.ROLE_USER);
//            adminUser = userService.register(admin, admin, admin, admin, admin, Role.ROLE_ADMIN);
            dataInitialized = true;
        }
    }

    @Test
    public void testScenario() throws Exception {
        CoursesPage coursesPage = CoursesPage.to(this.driver);
        coursesPage.assertElemts(0, 0, 0);
        LoginPage loginPage = LoginPage.openLogin(this.driver);

        coursesPage = LoginPage.doLogin(this.driver, loginPage, "admin", "admin");
        coursesPage.assertElemts(0, 0,  1);

        coursesPage = AddCourse.addCourse(this.driver, "test", "100",
                t1.getName() +" " + t1.getSurname(), Type.WINTER);
        coursesPage.assertElemts(1, 1,  1);

        coursesPage = AddCourse.addCourse(this.driver, "test2", "200",
                t2.getName() +" " + t2.getSurname(), Type.WINTER);
        coursesPage.assertElemts(2, 2,  1);

        coursesPage.getDeleteButtons().get(1).click();
        coursesPage.assertElemts(1, 1,  1);

        loginPage = LoginPage.logout(this.driver);
        coursesPage = CoursesPage.to(this.driver);
        coursesPage.assertElemts(0, 0, 0);


    }


}

