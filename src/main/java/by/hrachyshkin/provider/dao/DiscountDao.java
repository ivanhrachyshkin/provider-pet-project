package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Discount;

import java.util.List;

public interface DiscountDao extends Dao<Discount> {

   boolean isExistById(Integer id) throws DaoException;

   boolean isExistByName(String name) throws DaoException;

   boolean isExistByNotIdAndName(Integer id, String name) throws DaoException;

   List<Discount> findAndSortByValue() throws DaoException;

   List<Discount> findAndFilterByType(final Discount.Type type) throws DaoException;

   List<Discount> findAndFilterAndSort(final Discount.Type type) throws DaoException;
}
