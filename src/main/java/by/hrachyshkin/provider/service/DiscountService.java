package by.hrachyshkin.provider.service;

import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Discount;

import java.util.List;

public interface DiscountService extends Service<Discount> {

    boolean isExistByName(String name)
            throws ServiceException, TransactionException;

    List<Discount> findAndSortByValue(Integer offset)
            throws ServiceException, TransactionException;

    List<Discount> findAndFilterByTypeOffset(Discount.Type type, int offset)
            throws ServiceException, TransactionException;

    List<Discount> findAndFilterByTypeAndSortByValue(Discount.Type type)
            throws ServiceException, TransactionException;

    List<Discount> findDiscountsForPromotion(Integer tariffId)
            throws ServiceException, TransactionException;
}
