package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.AuthorBi;
import by.duzh.springframework.springdata.jpa.entity.BookBi;
import by.duzh.springframework.springdata.jpa.repository.AuthorBiRepository;
import by.duzh.springframework.springdata.jpa.repository.BookBiRepository;
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
public class ManyToManyBidirectionalTest {
    @Autowired
    private BookBiRepository bookBiRepository;

    @Autowired
    private AuthorBiRepository authorBiRepository;

    @Test
    void findBooks() {
        var books = bookBiRepository.findAll();
        assertThat(books).hasSize(3);
    }

    @Test
    void findAuthors() {
        var authors = authorBiRepository.findAll();
        assertThat(authors).hasSize(2);
    }

    @Test
    void getBookById() {
        var book = bookBiRepository.findById(1);
        assertTrue(book.isPresent());
    }

    @Test
    void getAuthorById() {
        var author = authorBiRepository.findById(1);
        assertTrue(author.isPresent());
    }

    @Rollback
    @Test
    void addNewBookWithNewAuthor() {
        var author = AuthorBi.builder().name("Test Author").build();

        var book = BookBi.builder().title("Test Book").build();
        book.addAuthor(author);

        var res = bookBiRepository.save(book);
        assertNotNull(res.getId());
    }

    @Rollback
    @Test
    void addNewAuthorWithNewBook() {
        var book = BookBi.builder().title("Test Book").build();

        var author = AuthorBi.builder().name("Test Author").build();
        author.addBook(book);

        var res = authorBiRepository.save(author);
        assertNotNull(res.getId());
    }

    @Rollback
    @Test
    void addNewAuthorToTheBook() {
        var author = AuthorBi.builder().name("Test Author").build();

        var bookOpt = bookBiRepository.findById(1);
        assertTrue(bookOpt.isPresent());

        var book = bookOpt.get();
        book.addAuthor(author);

        var res = bookBiRepository.save(book);
        assertNotNull(res.getId());
    }

    @Rollback
    @Test
    void addNewBookToTheAuthor() {
        var book = BookBi.builder().title("Test Book").build();

        var authorOpt = authorBiRepository.findById(1);
        assertTrue(authorOpt.isPresent());

        var author = authorOpt.get();
        author.addBook(book);

        var res = authorBiRepository.save(author);
        assertNotNull(res.getId());
    }

}
