package by.hrachyshkin.service;

import by.hrachyshkin.dao.transaction.Transaction;
import lombok.Setter;

public abstract class ServiceImpl {

    @Setter
    protected Transaction transaction = null;
}
