package by.duzh.springframework.app.dao.foo;

import by.duzh.springframework.app.model.foo.Foo;

public interface FooDao {
    Foo findById(Long id);
}