package mk.ukim.finki.wp.lab.model;


public class Teacher {

    private Long id;
    private String name;
    private String surname;

    public Teacher(String name, String surname)
    {
        this.id = (long) (Math.random()*1000);
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s", this.name, this.surname);
    }
}
