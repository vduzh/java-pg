package by.duzh.springframework.cglib.proxy;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnhancerTest {
    public static class BarDao {
        public String findById(int id) {
            return "bar";
        }
    }

    public static class BarDaoMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Object res = methodProxy.invokeSuper(object, args);

            if (method.getName().equals("findById")) {
                return res + "-" + args[0];
            }

            return res;
        }
    }

    @Test
    void test() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new BarDaoMethodInterceptor());
        enhancer.setSuperclass(BarDao.class);

        BarDao dao = (BarDao) enhancer.create();

        assertEquals("bar-123", dao.findById(123));
    }
}