package by.hrachyshkin.dao.entity_dao.discount_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Discount;

import java.util.List;

public interface DiscountDao extends Dao<Discount> {

   boolean isExistById(Integer id) throws DaoException;

   boolean isExistByName(String name) throws DaoException;

   List<Discount> findAndSortByValue() throws DaoException;

   List<Discount> findAndFilterByType(final Discount.Type type) throws DaoException;

   List<Discount> findAndFilterAndSort(final Discount.Type type) throws DaoException;
}
