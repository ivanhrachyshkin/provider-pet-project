package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;

import java.util.List;

public interface DiscountService extends Service<Discount> {

    List<Discount> findAndSortByValue() throws ServiceException, TransactionException;

    List<Discount> findAndFilterByType(final Discount.Type type) throws ServiceException, TransactionException;

    List<Discount> findAndFilterAndSort(final Discount.Type type) throws ServiceException, TransactionException;

    List<Discount> findDiscountsForTariff(final Integer tariffId) throws ServiceException, TransactionException;
    }
