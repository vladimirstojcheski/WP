package mk.ukim.finki.wp.lab.model.exceptions;

public class FillAllFieldsException extends RuntimeException{
    public FillAllFieldsException(String name, String description)
    {
        super(String.format("Please fill all the fields"));
    }
}
