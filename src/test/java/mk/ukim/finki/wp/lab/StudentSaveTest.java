package mk.ukim.finki.wp.lab;

import mk.ukim.finki.wp.lab.model.Student;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wp.lab.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wp.lab.repository.jpa.JpaStudentRepository;
import mk.ukim.finki.wp.lab.service.impl.StudentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class StudentSaveTest {

    @Mock
    private JpaStudentRepository studentRepository;

    private StudentServiceImpl service;

    @Before
    public void init()
    {
        MockitoAnnotations.initMocks(this);
        Student student = new Student("userr", "userr", "userr", "userr");

        Mockito.when(this.studentRepository.save(Mockito.any(Student.class))).thenReturn(student);

        this.service = Mockito.spy(new StudentServiceImpl(studentRepository));
    }

    @Test
    public void testSuccessSave()
    {
        Student student = this.service.save("userr", "userr", "userr", "userr");
        Mockito.verify(this.service).save("userr", "userr", "userr", "userr");

        Assert.assertNotNull("Student is null", student);

        Assert.assertEquals("username do not match", "userr", student.getUsername());
        Assert.assertEquals("password do not match", "userr", student.getPassword());
        Assert.assertEquals("name do not match", "userr", student.getName());
        Assert.assertEquals("surname do not match", "userr", student.getSurname());
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.save(null, "password",  "name", "surname"));
        Mockito.verify(this.service).save(null, "password",  "name", "surname");
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.save(username, "password",  "name", "surname"));
        Mockito.verify(this.service).save(username, "password",  "name", "surname");
    }

    @Test
    public void testNullPassword() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.save("username", null,  "name", "surname"));
        Mockito.verify(this.service).save("username", null,  "name", "surname");
    }

    @Test
    public void testEmptyPassword() {
        String password = "";
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.service.save("username", password,  "name", "surname"));
        Mockito.verify(this.service).save("username", password,  "name", "surname");
    }

    @Test
    public void testDuplicateUsername() {
        Student student = new Student("username", "password", "name", "surname");
        Mockito.when(this.studentRepository.findById(Mockito.anyString())).thenReturn(Optional.of(student));
        String username = "username";
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                UsernameAlreadyExistsException.class,
                () -> this.service.save(username,  "password", "name", "surname"));
        Mockito.verify(this.service).save(username, "password", "name", "surname");
    }


}
