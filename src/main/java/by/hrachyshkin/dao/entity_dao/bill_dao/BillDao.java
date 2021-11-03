package by.hrachyshkin.dao.entity_dao.bill_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.EntityDao;
import by.hrachyshkin.entity.Account;
import by.hrachyshkin.entity.Bill;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface BillDao extends EntityDao<Bill> {

    boolean isExistById(Integer id) throws DaoException;

    List<Bill> findAndSort(Sort sort) throws DaoException;

    List<Bill> findAndFilter(Filter filter) throws DaoException;

    List<Bill> findAndFilterAndSort(Filter filter, Sort sort) throws DaoException;

}
