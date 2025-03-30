package ru.tatarinov.mvc.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.tatarinov.mvc.model.Book;
import ru.tatarinov.mvc.model.Person;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(MockitoExtension.class)
class BookDAOTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDAO bookDAO;

    @Test
    void testShowBooks() {
        Mockito.when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(DataTest.book));

        List<Book> list = List.of(DataTest.book);

        List<?> result = bookDAO.showBooks();

        assertFalse(result.isEmpty());

        assertEquals(bookDAO.showBooks(), list);
    }

    @Test
    void getBookOwner() {
        Mockito.when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(List.of(DataTest.person));


        assertTrue(bookDAO.getBookOwner(1).isPresent());
        Optional<Person> obj = Optional.ofNullable(DataTest.person);

        assertEquals(bookDAO.getBookOwner(1), obj);
    }
}
