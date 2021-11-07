package by.hrachyshkin.service;

import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface Service<T> {

    public void add(T t) throws ServiceException;

    List<T> find(final Filter filter, final Sort sort) throws ServiceException;

    T findOneById(final Integer id) throws ServiceException;

    void update(T t) throws ServiceException;

    void delete(Integer id) throws ServiceException;
}
