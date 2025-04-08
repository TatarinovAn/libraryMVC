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
import ru.tatarinov.mvc.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {
    @Mock
    private PeopleRepository peopleRepository;

    @InjectMocks
    private PeopleService peopleService;

    @Test
    void findAll() {
        Mockito.when(peopleRepository.findAll()).thenReturn(List.of(DataTest.person));

        List<Person> list = List.of(DataTest.person);

        List<?> result = peopleService.findAll();


        assertFalse(result.isEmpty());
        assertEquals(result, list);
    }

    @Test
    void findOne() {
        Mockito.when(peopleRepository.findById(anyInt())).thenReturn(Optional.ofNullable(DataTest.person));
        Optional<Person> person = peopleService.findOne(1);


        assertTrue(person.isPresent());
        assertEquals(person, Optional.ofNullable(DataTest.person));
    }

    @Test
    void getPersonFindByName() {
        Mockito.when(peopleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(DataTest.person));

        Optional<Person> person = peopleService.getPersonFindByName("test");

        assertTrue(person.isPresent());
        assertEquals(person, Optional.ofNullable(DataTest.person));
    }

    @Test
    void getBooksByPersonId() {
        DataTest.person.setBooks(List.of(DataTest.book));
        Mockito.when(peopleRepository.findById(anyInt())).thenReturn(Optional.ofNullable(DataTest.person));
        List<Book> books = peopleService.getBooksByPersonId(1);
        assertFalse(books.isEmpty());
        assertEquals(books, List.of(DataTest.book));
    }
}
