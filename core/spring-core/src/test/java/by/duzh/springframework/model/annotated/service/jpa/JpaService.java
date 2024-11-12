package by.duzh.springframework.model.annotated.service.jpa;

import by.duzh.springframework.model.annotated.dao.Dao;
import by.duzh.springframework.model.annotated.logger.Logger;
import by.duzh.springframework.model.annotated.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service("jpaService")
public class JpaService implements Service {
    private Dao dao;
    private Logger logger;

    @Autowired
    public JpaService(@Qualifier("jpaDao") Dao dao, Logger logger) {
        this.dao = dao;
        this.logger = logger;
    }

    @Override
    public Dao getDao() {
        return dao;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

}
