package ru.tatarinov.mvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tatarinov.mvc.dao.BookDAO;
import ru.tatarinov.mvc.dao.PersonDAO;
import ru.tatarinov.mvc.model.Book;
import ru.tatarinov.mvc.model.Person;
import ru.tatarinov.mvc.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {


    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;


    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }


    @GetMapping("/{id}")
    public String say(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.showBook(id));

        Optional<Person> peopleOwner = bookDAO.getBookOwner(id);

        if (peopleOwner.isPresent()) {
            model.addAttribute("owner", peopleOwner.get());
        } else {
            model.addAttribute("people", personDAO.showPeople());
        }

        return "books/show";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.showBooks());
        return "books/index";
    }


    @GetMapping("/new")
    public String newPeople(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        bookDAO.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute(bookDAO.showBook(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookDAO.update(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person selectedPerson,
                         @PathVariable("id") int id) {
        bookDAO.givePersonBook(selectedPerson, id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.deletePersonBook(id);
        return "redirect:/books/" + id;
    }


}
