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
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<?> showBooks() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBook(int bookId) {

        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{bookId}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Book> showBook(String name) {

        return jdbcTemplate.query("SELECT * FROM Book WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }


    public void saveBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book (person_id, name, author, year_of_production) VALUES (null, ?, ?, ?)", book.getName(), book.getAuthor(), book.getYearOfProduction());
    }

    public void update(Book updateBook, int bookId) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year_of_production = ? WHERE book_id = ?",
                updateBook.getName(), updateBook.getAuthor(), updateBook.getYearOfProduction(), bookId);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public void givePersonBook(Person selectPerson, int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?", selectPerson.getPersonId(), id);
    }

    public void deletePersonBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        // Выбираем все колонки таблицы Person из объединенной таблицы
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.person_id " +
                        "WHERE Book.book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }


//    public void takePushBook(Book pushbook, int bookId) {
//        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?",
//                pushbook.getPersonId(), pushbook.getBookId());
//    }
}
