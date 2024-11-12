package by.duzh.springframework.app.service.foo.impl;

import by.duzh.springframework.app.dao.foo.FooDao;
import by.duzh.springframework.app.model.foo.Foo;
import by.duzh.springframework.app.service.foo.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FooServiceImpl implements FooService {
    @Autowired
    private FooDao dao;

    @Override
    @Transactional(readOnly = true)
    public Foo findById(Long id) {
        return dao.findById(id);
    }
}