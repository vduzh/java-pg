package by.duzh.springframework.orm.hibernate5;

import by.duzh.springframework.orm.hibernate5.beans.Foo;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(HibernateTransactionManagerTestConfig.class)
public class HibernateTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional(readOnly = true)
    void test() {
/*
        var list = sessionFactory.getCurrentSession().createQuery("from Foo", Foo.class).list();
        assertEquals(2, list.size());
*/

        System.out.println(sessionFactory.getCurrentSession().get(Foo.class, 1L));
    }

}
