package ru.tatarinov.mvc;

import ru.tatarinov.mvc.models.Book;
import ru.tatarinov.mvc.models.Person;

import java.util.List;

public class DataTest {


    public static Person person = new Person(1, "test", 1953);
    public static Book book = new Book(1, "test", "test", 1992, person);
}
