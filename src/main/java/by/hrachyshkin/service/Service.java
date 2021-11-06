package by.hrachyshkin.service;

import java.util.List;

public interface Service<T> {

    List<T> find() throws ServiceException;
}
