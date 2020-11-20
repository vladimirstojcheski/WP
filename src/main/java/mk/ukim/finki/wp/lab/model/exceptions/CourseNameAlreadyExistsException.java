package mk.ukim.finki.wp.lab.model.exceptions;

public class CourseNameAlreadyExistsException extends RuntimeException{
    public CourseNameAlreadyExistsException(String name)
    {
        super(String.format("Course %s already exists", name));
    }
}
