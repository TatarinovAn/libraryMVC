package ru.tatarinov.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tatarinov.mvc.model.Book;
import ru.tatarinov.mvc.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> showPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPerson(int id) {

        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> showPerson(String name) {

        return jdbcTemplate.query("SELECT * FROM Person WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }


    public void savePerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, year_of_birth) VALUES (?, ?)", person.getName(), person.getYearOfBirth());
    }

    public void update(Person updatePerson, int id) {
        jdbcTemplate.update("UPDATE Person SET name = ?, year_of_birth = ? WHERE person_id = ?",
                updatePerson.getName(), updatePerson.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);
    }

    public List<Book> getBooksByPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

}

