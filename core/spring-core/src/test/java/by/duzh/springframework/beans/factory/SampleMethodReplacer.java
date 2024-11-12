package by.duzh.springframework.beans.factory;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class SampleMethodReplacer implements MethodReplacer {
    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        if ("getReplacedValue".equals(method.getName())) {
            return "replaced";
        }
        throw new RuntimeException("Invalid method passed in");
    }
}
