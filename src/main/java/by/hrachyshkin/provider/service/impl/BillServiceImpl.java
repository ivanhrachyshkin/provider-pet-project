package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Account;
import by.hrachyshkin.provider.model.Bill;
import by.hrachyshkin.provider.service.BillService;
import by.hrachyshkin.provider.service.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ResourceBundle;


/**
 * Represents operations for Bill.
 *
 * @see Bill
 */
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final static Logger LOGGER =
            LoggerFactory.getLogger(BillServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public List<Bill> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL);
            final List<Bill> bills = billDao.find();
            LOGGER.debug("method find finish ");
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> findAndSortByDate()
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndSortByDate starts ");
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL);
            final List<Bill> bills = billDao.findAndSortByDate();
            LOGGER.debug("method findAndSortByDate finish ");
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> findAndFilterBySubscriptionId(
            final Integer subscriptionId)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterBySubscriptionId starts ");
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL);
            final List<Bill> bills =
                    billDao.findAndFilterBySubscriptionId(subscriptionId);
            LOGGER.debug("method findAndFilterBySubscriptionId finish ");
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Bill> findAndFilterAndSortOffset(final Integer subscriptionId,
                                                 final int offset)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterAndSortOffset starts ");
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL);
            final List<Bill> bills =
                    billDao.findAndFilterAndSortOffset(subscriptionId, offset);
            LOGGER.debug("method findAndFilterAndSortOffset finish ");
            transactionImpl.commit();
            return bills;

        } catch (TransactionException | DaoException e) {
            LOGGER.debug(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Bill findOneById(final Integer id) throws ServiceException {
        LOGGER.debug(rb.getString("bill.find.one.by.id.unsupported"
                + ".exception"));
        throw new UnsupportedOperationException(rb.getString("bill.find"
                + ".one.by.id.unsupported.exception"));
    }

    @Override
    public void add(final Bill bill)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method add starts ");
            final BillDao billDao = transactionImpl.createDao(DaoKeys.BILL);

            if (bill.getValue() == null
                    || bill.getDate() == null) {
                LOGGER.error(rb.getString("bill.add.empty.input"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.add.empty"
                        + ".input.exception"));
            }

            if (billDao.isExists(bill.getSubscriptionId(), bill.getValue(),
                    bill.getDate())) {
                LOGGER.error(rb.getString("bill.add.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.add.exist"
                        + ".exception"));
            }

            if (bill.getValue() < 0) {
                LOGGER.error(rb.getString("bill.add.negative.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("bill.add"
                        + ".negative.exception"));
            }

            billDao.add(bill);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Bill bill)
            throws ServiceException, TransactionException {
        LOGGER.error(rb.getString("bill.update.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("bill.update"
                + ".unsupported.exception"));
    }

    @Override
    public void delete(final Integer subscriptionId)
            throws ServiceException, TransactionException {
        LOGGER.error(rb.getString("bill.delete.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("bill.delete"
                + ".unsupported.exception"));
    }
}
