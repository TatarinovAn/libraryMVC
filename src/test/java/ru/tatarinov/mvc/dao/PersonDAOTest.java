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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PersonDAOTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PersonDAO personDAO;

    @Test
    void showPeopleTest() {
        Mockito.when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(DataTest.person));


        List<Person> list = List.of(DataTest.person);

        List<?> result = personDAO.showPeople();

        assertFalse(result.isEmpty());

        assertEquals(personDAO.showPeople(), list);
    }

    @Test
    void getBooksByPerson() {
        Mockito.when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenReturn(List.of(DataTest.book));


        assertFalse(personDAO.getBooksByPerson(1).isEmpty());
        List<Book> list = List.of(DataTest.book);

        assertEquals(personDAO.getBooksByPerson(1), list);
    }
}
