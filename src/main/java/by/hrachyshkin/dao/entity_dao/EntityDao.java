package by.hrachyshkin.dao.entity_dao;

import by.hrachyshkin.dao.DaoException;

import java.util.List;

public interface EntityDao<T> {

    void add(T t) throws DaoException;

    List<T> find() throws DaoException;

    T findOneById(int id) throws DaoException;

    void update(T t) throws DaoException, DaoException;

    void delete(int id) throws DaoException;

}
