package ru.tatarinov.mvc.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tatarinov.mvc.DataTest;
import ru.tatarinov.mvc.models.Book;
import ru.tatarinov.mvc.models.Person;
import ru.tatarinov.mvc.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class BooksServiceTest {


    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BooksService booksService;


    @Test
    void findAll() {
        Mockito.when(booksRepository.findAll()).thenReturn(List.of(DataTest.book));

        List<Book> list = List.of(DataTest.book);

        List<?> result = booksService.findAll();


        assertFalse(result.isEmpty());
        assertEquals(result, list);
    }


    @Test
    void findOne() {
        Mockito.when(booksRepository.findById(anyInt())).thenReturn(Optional.ofNullable(DataTest.book));
        Optional<Book> book = booksService.findOne(1);


        assertTrue(book.isPresent());
        assertEquals(book, Optional.ofNullable(DataTest.book));

    }

    @Test
    void findOwner() {
        Mockito.when(booksRepository.findById(anyInt())).thenReturn(Optional.ofNullable(DataTest.book));
        Optional<Person> person = booksService.findOwner(1);


        assertTrue(person.isPresent());
        assertEquals(person, Optional.ofNullable(DataTest.person));
    }

    @Test
    void getBookFindByName() {
        Mockito.when(booksRepository.findByName(anyString())).thenReturn(Optional.ofNullable(DataTest.book));

        Optional<Book> book = booksService.getBookFindByName("test");

        assertTrue(book.isPresent());
        assertEquals(book, Optional.ofNullable(DataTest.book));
    }
}
