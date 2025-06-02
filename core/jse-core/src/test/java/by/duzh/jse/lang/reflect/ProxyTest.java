package by.duzh.jse.lang.reflect;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Proxy;

public class ProxyTest {
    interface FooDao {
        String find(int id);
    }

    static class InMemoryFooDao implements FooDao {
        @Override
        public String find(int id) {
            return "foo";
        }
    }

    @Test
    public void newProxyInstance() throws Exception {
        FooDao dao = (FooDao) Proxy.newProxyInstance(FooDao.class.getClassLoader(), new Class<?>[]{FooDao.class},
                (proxy, method, args) -> {
                    System.out.println("BEFORE:" + method.getName());

                    InMemoryFooDao target = new InMemoryFooDao();
                    Object result = method.invoke(target, args);

                    System.out.println("AFTER:" + method.getName());

                    return result;
                });

        String foo = dao.find(123);
        Assertions.assertEquals("foo", foo);
    }
}
