package ru.tatarinov.mvc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tatarinov.mvc.dao.BookDAO;
import ru.tatarinov.mvc.dao.PersonDAO;
import ru.tatarinov.mvc.model.Book;
import ru.tatarinov.mvc.model.Person;


@Component
public class BookValidator implements Validator {

    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (bookDAO.showBook(book.getName()).isPresent()) {
            errors.rejectValue("name", "500", "Книга с таким названием уже существует");
        }
    }
}
