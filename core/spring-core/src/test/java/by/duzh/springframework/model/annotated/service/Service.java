package by.duzh.springframework.model.annotated.service;

import by.duzh.springframework.model.annotated.dao.Dao;
import by.duzh.springframework.model.annotated.logger.Logger;

public interface Service {
    Dao getDao();

    Logger getLogger();

}
