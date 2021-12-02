package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Discount;

import java.util.List;

public interface DiscountDao extends Dao<Discount> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByName(String name) throws DaoException;

    boolean isExistByNotIdAndName(Integer id, String name) throws DaoException;

    List<Discount> findAndSortByValue(Integer offset) throws DaoException;

    List<Discount> findAndFilterByType(Discount.Type type, int offset)
            throws DaoException;

    List<Discount> findAndFilterAndSort(Discount.Type type) throws DaoException;
}
