package by.hrachyshkin.dao.entity_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.entity.Criteria;

import java.util.List;

public interface EntityDao<T> {

    void create(T t) throws DaoException;

    List<T> find(Criteria criteria) throws DaoException;

    T findOneById(int id) throws DaoException;

    void update(T t) throws DaoException;

    void delete(int id) throws DaoException;

}
