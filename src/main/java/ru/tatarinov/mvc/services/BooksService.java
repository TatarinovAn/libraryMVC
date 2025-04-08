package ru.tatarinov.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tatarinov.mvc.models.Book;
import ru.tatarinov.mvc.models.Person;
import ru.tatarinov.mvc.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Optional<Book> findOne(int id) {
        return booksRepository.findById(id);
    }

    public Optional<Person> findOwner(int id) {
        Book book = booksRepository.findById(id).orElse(null);
        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book bookUpdate) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        bookUpdate.setId(id);
        bookUpdate.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(bookUpdate);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }


    @Transactional
    public void assign(Person selectedPerson, int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                }
        );
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );
    }


    public Optional<Book> getBookFindByName(String name) {
        return booksRepository.findByName(name);
    }


}
