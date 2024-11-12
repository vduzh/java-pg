package by.duzh.springframework.model.annotated.service.jdbc;

import by.duzh.springframework.model.annotated.dao.Dao;
import by.duzh.springframework.model.annotated.logger.Logger;
import by.duzh.springframework.model.annotated.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service("jdbcService")
public class JdbcService implements Service {
    private Dao dao;

    @Autowired
    private Logger logger;

    @Autowired
    @Qualifier("jdbcDao")
    public void setDao(Dao dao) {
        this.dao = dao;
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
