package by.duzh.springframework.app.dao.foo.hibernate;

import by.duzh.springframework.app.dao.foo.FooDao;
import by.duzh.springframework.app.model.foo.Foo;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("hibernate")
public class FooDaoImpl implements FooDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Foo findById(Long id) {
        System.out.println(getClass() + " findById is working..." );
        return sessionFactory.getCurrentSession().get(Foo.class, id);
    }
}