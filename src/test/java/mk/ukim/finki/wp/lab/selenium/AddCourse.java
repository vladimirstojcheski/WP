package mk.ukim.finki.wp.lab.selenium;

import mk.ukim.finki.wp.lab.model.Type;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AddCourse extends AbstractPage {

    private WebElement name;
    private WebElement description;
    private WebElement teacherId;
    private WebElement type;
    private WebElement submit;

    public AddCourse(WebDriver driver) {
        super(driver);
    }

    public static CoursesPage addCourse(WebDriver driver, String name, String description, String teacherId, Type type) {
        get(driver, "/courses/add-form");
        AddCourse addCourse = PageFactory.initElements(driver, AddCourse.class);
        addCourse.name.sendKeys(name);
        addCourse.description.sendKeys(description);
        addCourse.teacherId.click();
        addCourse.teacherId.findElement(By.xpath("//option[. = '" + teacherId + "']")).click();
        addCourse.type.click();
        addCourse.type.findElement(By.xpath("//option[. = '" + type + "']")).click();

        addCourse.submit.click();
        return PageFactory.initElements(driver, CoursesPage.class);
    }
}
