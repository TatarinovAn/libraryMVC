package ru.tatarinov.mvc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tatarinov.mvc.models.Book;
import ru.tatarinov.mvc.models.Person;
import ru.tatarinov.mvc.services.BooksService;
import ru.tatarinov.mvc.services.PeopleService;
import ru.tatarinov.mvc.util.BookValidator;

import jakarta.validation.Valid;

import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BooksController {


    private final BookValidator bookValidator;
    private final PeopleService peopleService;
    private final BooksService booksService;


    @Autowired
    public BooksController(BookValidator bookValidator, PeopleService peopleService, BooksService booksService) {
        this.bookValidator = bookValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }


    @GetMapping("/{id}")
    public String say(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = booksService.findOne(id).orElse(null);
        model.addAttribute("book", book);

        Optional<Person> peopleOwner = booksService.findOwner(id);

        if (peopleOwner.isPresent()) {
            model.addAttribute("owner", peopleOwner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }

        return "books/show";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
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
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute(booksService.findOne(id).get());

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("books") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person selectedPerson,
                         @PathVariable("id") int id) {
        booksService.assign(selectedPerson, id);

        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {

        // Optional<Person> peopleOwner = booksService.findOwner(id);


        booksService.release(id);


        return "redirect:/books/" + id;
    }


}
