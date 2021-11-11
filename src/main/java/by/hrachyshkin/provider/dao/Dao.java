package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Model;

import java.util.List;

public interface Dao<T extends Model> {

    void add(T t) throws DaoException;

    List<T> find() throws DaoException;

    T findOneById(Integer id) throws DaoException;

    void update(T t) throws DaoException;

    void delete(Integer id) throws DaoException;

}
