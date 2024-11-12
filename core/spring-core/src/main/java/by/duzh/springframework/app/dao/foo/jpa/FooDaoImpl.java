package by.duzh.springframework.app.dao.foo.jpa;

import by.duzh.springframework.app.dao.foo.FooDao;
import by.duzh.springframework.app.model.foo.Foo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Profile("jpa")
public class FooDaoImpl implements FooDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Foo findById(Long id) {
        System.out.println(getClass() + " findById is working...");
        return entityManager.find(Foo.class, id);
    }
}