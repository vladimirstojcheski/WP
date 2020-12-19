package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import lombok.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Student {

    @Id
    private String username;

    private String password;

    private String name;

    private String surname;

    public Student(String username, String password, String name, String surname)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public Student() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
