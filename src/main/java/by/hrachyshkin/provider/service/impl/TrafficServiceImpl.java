package by.hrachyshkin.provider.service.impl;

import by.hrachyshkin.provider.dao.*;
import by.hrachyshkin.provider.model.Traffic;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ResourceBundle;

/**
 * Represents operations for Traffic.
 *
 * @see Traffic
 */
@RequiredArgsConstructor
public class TrafficServiceImpl implements TrafficService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TrafficServiceImpl.class);
    private final Transaction transactionImpl;
    private final ResourceBundle rb;

    @Override
    public List<Traffic> find() throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method find starts ");
            final TrafficDao trafficDao =
                    transactionImpl.createDao(DaoKeys.TRAFFIC);
            final List<Traffic> traffic = trafficDao.find();
            LOGGER.debug("method find finish ");
            transactionImpl.commit();
            return traffic;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findAndSortByDate()
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndSortByDate starts ");
            final TrafficDao trafficDao =
                    transactionImpl.createDao(DaoKeys.TRAFFIC);
            final List<Traffic> traffics = trafficDao.findAndSortByDate();
            LOGGER.debug("method findAndSortByDate finish ");
            transactionImpl.commit();
            return traffics;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findAndFilterBySubscriptionId(
            final Integer subscriptionId)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterBySubscriptionId starts ");
            final TrafficDao trafficDao =
                    transactionImpl.createDao(DaoKeys.TRAFFIC);
            final List<Traffic> traffics =
                    trafficDao.findAndFilterBySubscriptionId(subscriptionId);
            LOGGER.debug("method findAndFilterBySubscriptionId finish ");
            transactionImpl.commit();
            return traffics;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Traffic> findAndFilterAndSortOffset(
            final Integer subscriptionId, final int offset)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method findAndFilterAndSortOffset starts ");
            final TrafficDao trafficDao =
                    transactionImpl.createDao(DaoKeys.TRAFFIC);
            final List<Traffic> traffics = trafficDao
                    .findAndFilterAndSortOffset(subscriptionId, offset);
            LOGGER.debug("method findAndFilterAndSortOffset finish ");
            transactionImpl.commit();
            return traffics;

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public Traffic findOneById(final Integer id) throws ServiceException {
        LOGGER.error(rb.getString("traffic.find.one.by.id.unsupported"
                + ".exception"));
        throw new UnsupportedOperationException(rb.getString("traffic"
                + ".find.one.by.id.unsupported.exception"));
    }

    @Override
    public void add(final Traffic traffic)
            throws ServiceException, TransactionException {

        try {
            LOGGER.debug("method add starts ");
            final TrafficDao trafficDao =
                    transactionImpl.createDao(DaoKeys.TRAFFIC);

            if (traffic.getValue() == null
                    || traffic.getDate() == null) {
                LOGGER.error(rb.getString("traffic.add.empty.input"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("traffic.add"
                        + ".empty.input.exception"));
            }

            if (trafficDao.isExists(traffic)) {
                LOGGER.error(rb.getString("traffic.add.exist.exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("traffic.add"
                        +
                        ".exist.exception"));
            }

            if (traffic.getValue() < 0) {
                LOGGER.error(rb.getString("traffic.add.negative"
                        + ".exception"));
                transactionImpl.rollback();
                throw new ServiceException(rb.getString("traffic.add"
                        + ".negative.exception"));
            }

            trafficDao.add(traffic);
            LOGGER.debug("method add finish ");
            transactionImpl.commit();

        } catch (TransactionException | DaoException e) {
            LOGGER.error(e.getMessage());
            transactionImpl.rollback();
            transactionImpl.commit();
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void update(final Traffic traffic) throws ServiceException {
        LOGGER.error(rb.getString("traffic.update.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("traffic"
                + ".update.unsupported.exception"));
    }

    @Override
    public void delete(final Integer subscriptionId)
            throws ServiceException, TransactionException {
        LOGGER.error(rb.getString("traffic.delete.unsupported.exception"));
        throw new UnsupportedOperationException(rb.getString("traffic"
                + ".delete.unsupported.exception"));
    }
}
