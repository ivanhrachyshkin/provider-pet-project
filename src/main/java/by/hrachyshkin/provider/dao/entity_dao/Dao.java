package by.hrachyshkin.provider.dao.entity_dao;

import by.hrachyshkin.provider.dao.DaoException;

import java.util.List;

public interface Dao<T> {

    void add(T t) throws DaoException;

    List<T> find() throws DaoException;

    T findOneById(Integer id) throws DaoException;

    void update(T t) throws DaoException, DaoException;

    void delete(Integer id) throws DaoException;

}
