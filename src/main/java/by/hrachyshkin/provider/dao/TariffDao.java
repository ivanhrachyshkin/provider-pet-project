package by.hrachyshkin.provider.dao;

import by.hrachyshkin.provider.model.Tariff;

import java.util.List;

public interface TariffDao extends Dao<Tariff> {

    boolean isExistById(Integer id) throws DaoException;

    boolean isExistByName(String name) throws DaoException;

    boolean isExistByNotIdAndName(Integer id, String name)
            throws DaoException;

    List<Tariff> findAndSortBySpeedAndPriceOffset(int offset)
            throws DaoException;

    List<Tariff> findAndFilterByTypeOffset(Tariff.Type type, int offset)
            throws DaoException;

    List<Tariff> findAndFilterAndSort(Tariff.Type type) throws DaoException;
}
