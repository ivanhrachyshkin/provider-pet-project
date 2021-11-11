package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.entity.Discount;

import java.util.List;

public interface DiscountService extends Service<Discount> {

    List<Discount> findAndSortByValue() throws ServiceException;

    List<Discount> findAndFilterByType(final Discount.Type type) throws ServiceException;

    List<Discount> findAndFilterAndSort(final Discount.Type type) throws ServiceException;
    }
