package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Model;

import java.util.List;

public interface Service<T extends Model> {

    void add(T t) throws ServiceException, TransactionException, TransactionException;

    List<T> find() throws ServiceException, TransactionException;

    T findOneById(final Integer id) throws ServiceException, TransactionException;

    void update(T t) throws ServiceException, TransactionException;

    void delete(Integer id) throws ServiceException, TransactionException;
}
