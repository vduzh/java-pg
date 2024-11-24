package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class ManyToManyUniTest {
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findAll() {
        var books = repository.findAll();
        assertThat(books).hasSize(3);
    }

    @Test
    void getById() {
        var book = repository.findById(1);
        assertTrue(book.isPresent());
    }

    @Rollback
    @Test
    void add() {
        var author = authorRepository.findById(2);
        assertTrue(author.isPresent());

        var book = Book.builder().name("Green Apple").build();
        book.addAuthor(author.get());

        var res = repository.save(book);
        assertNotNull(res.getId());
    }
}
