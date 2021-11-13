package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Model;

import java.util.List;

public interface Dao<T extends Model> {

    List<T> find() throws DaoException;

    T findOneById(Integer id) throws DaoException;

    void add(T t) throws DaoException;

    void update(T t) throws DaoException;

    void delete(Integer id) throws DaoException;

}
