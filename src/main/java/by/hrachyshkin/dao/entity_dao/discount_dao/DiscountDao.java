package by.hrachyshkin.dao.entity_dao.discount_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Discount;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface DiscountDao extends EntityDao<Discount> {

   boolean isExistById(Integer id) throws DaoException;

   boolean isExistByName(String name) throws DaoException;

   List<Discount> findAndSort(Sort sort) throws DaoException;

   List<Discount> findAndFilter(Filter filter) throws DaoException;

   List<Discount> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException;
}
