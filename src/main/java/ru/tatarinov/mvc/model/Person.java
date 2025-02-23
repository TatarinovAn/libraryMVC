package ru.tatarinov.mvc.model;

import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {

    private int personId;


    @NotEmpty(message = "ФИО не может быть пустым")
    @Size(min = 2, max = 200, message = "ФИО может состоять из 2 и более символов, но не более 200")
    @Pattern(regexp = "[А-Я][а-я]+ [А-Я][а-я]+ [А-Я][а-я]+", message = "Кирилицей с большой буквы")
    private String name;
    @Min(value = 1900, message = "Год рождения должен быть больше 1900 года")
    private int yearOfBirth;


    public Person() {

    }

    public Person(int personId, String name, int yearOfBirth) {
        this.personId = personId;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
