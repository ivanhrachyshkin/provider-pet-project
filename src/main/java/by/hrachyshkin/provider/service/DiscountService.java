package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;

import java.util.List;

public interface DiscountService extends Service<Discount> {

    List<Discount> findAndSortByValue(Integer offset) throws ServiceException, TransactionException;

    List<Discount> findAndFilterByType(final Discount.Type type, int offset) throws ServiceException, TransactionException;

    List<Discount> findAndFilterAndSortByValue(final Discount.Type type) throws ServiceException, TransactionException;

    List<Discount> findDiscountsForPromotion(final Integer tariffId) throws ServiceException, TransactionException;
    }
