package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Work;
import by.duzh.springframework.springdata.jpa.entity.Writer;
import by.duzh.springframework.springdata.jpa.repository.WorkRepository;
import by.duzh.springframework.springdata.jpa.repository.WriterRepository;
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
public class ManyToManyBiTest {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private WriterRepository writerRepository;

    @Test
    void findWorks() {
        var works = workRepository.findAll();
        assertThat(works).hasSize(3);
    }

    @Test
    void findWriters() {
        var writers = writerRepository.findAll();
        assertThat(writers).hasSize(2);
    }

    @Test
    void getWorkById() {
        var work = workRepository.findById(1);
        assertTrue(work.isPresent());
    }

    @Test
    void getWriterById() {
        var writer = writerRepository.findById(1);
        assertTrue(writer.isPresent());
    }

    @Rollback
    @Test
    void addNewWorkWithNewWriter() {
        var writer = Writer.builder().name("Test Writer").build();

        var work = Work.builder().name("Test Work").build();
        work.addWriter(writer);

        var res = workRepository.save(work);
        assertNotNull(res.getId());
    }

    @Rollback
    @Test
    void addNewWriterWithNewWork() {
        var work = Work.builder().name("Test Work").build();

        var writer = Writer.builder().name("Test Writer").build();
        writer.addWork(work);

        var res = writerRepository.save(writer);
        assertNotNull(res.getId());
    }

    @Rollback
    @Test
    void addNewWriterToTheWork() {
        var writer = Writer.builder().name("Test Writer").build();

        var workOpt = workRepository.findById(1);
        assertTrue(workOpt.isPresent());

        var work = workOpt.get();
        work.addWriter(writer);

        var res = workRepository.save(work);
        assertNotNull(res.getId());
    }

    @Rollback
    @Test
    void addNewWorkToTheWriter() {
        var work = Work.builder().name("Test Work").build();

        var writerOpt = writerRepository.findById(1);
        assertTrue(writerOpt.isPresent());

        var writer = writerOpt.get();
        writer.addWork(work);

        var res = writerRepository.save(writer);
        assertNotNull(res.getId());
    }
}
