package by.hrachyshkin.provider.service;

import java.util.List;

public interface Service<T> {

    public void add(T t) throws ServiceException;

    List<T> find() throws ServiceException;

    T findOneById(final Integer id) throws ServiceException;

    void update(T t) throws ServiceException;

    void delete(Integer id) throws ServiceException;
}
