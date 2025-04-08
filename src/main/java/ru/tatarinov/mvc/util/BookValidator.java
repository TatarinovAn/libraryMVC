package ru.tatarinov.mvc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tatarinov.mvc.models.Book;
import ru.tatarinov.mvc.services.BooksService;


@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if (booksService.getBookFindByName(book.getName()).isPresent()) {
            errors.rejectValue("name", "500", "Книга с таким названием уже существует");
        }
    }
}
