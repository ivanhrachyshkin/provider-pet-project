package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Tariff;

import java.util.List;

public interface TariffService extends Service<Tariff>{

    public boolean isExist(final Integer id) throws ServiceException;

    List<Tariff> findAndSortBySpeedAndPrice() throws ServiceException;

    List<Tariff> findAndFilterByType(final Tariff.Type type) throws ServiceException;

    List<Tariff> findAndFilterAndSort(final Tariff.Type type) throws ServiceException;
}
