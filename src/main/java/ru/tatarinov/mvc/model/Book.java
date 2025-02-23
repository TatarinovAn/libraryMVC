package ru.tatarinov.mvc.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int bookId;
    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 2, max = 200, message = "Название книги может состоять из 2 и более символов, но не более 200")
    private String name;

    @NotEmpty(message = "Поле автор не может быть пустым")
    @Size(min = 2, max = 200, message = "Автор может состоять из 2 и более символов, но не более 200")
    private String author;
    @Min(value = 1000, message = "Год выпуска должен быть больше 1000 года")
    private int yearOfProduction;

    public Book() {

    }

    public Book(int bookId, String name, String author, int yearOfProduction) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}
