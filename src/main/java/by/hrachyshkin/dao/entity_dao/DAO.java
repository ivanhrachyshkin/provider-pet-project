package by.hrachyshkin.dao.entity_dao;

import by.hrachyshkin.dao.DAOException;

import java.util.List;

public interface DAO<T> {

    void create(T t) throws DAOException;

    List<T> findAll() throws DAOException;

    void update();

    void delete();
}
