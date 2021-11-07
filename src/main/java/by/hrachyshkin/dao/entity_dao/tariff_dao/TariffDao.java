package by.hrachyshkin.dao.entity_dao.tariff_dao;

import by.hrachyshkin.dao.DaoException;
import by.hrachyshkin.dao.entity_dao.Dao;
import by.hrachyshkin.entity.Tariff;
import by.hrachyshkin.entity.criteria.Filter;
import by.hrachyshkin.entity.criteria.Sort;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByName(String name) throws DaoException;

    List<Tariff> findAndSortBySpeedAndPrice() throws DaoException;

    List<Tariff> findAndFilterByType(Tariff.Type type) throws DaoException;

    List<Tariff> findAndFilterAndSort(Tariff.Type type) throws DaoException;
}
