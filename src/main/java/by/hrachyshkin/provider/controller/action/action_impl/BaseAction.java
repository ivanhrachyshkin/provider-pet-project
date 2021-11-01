package by.hrachyshkin.provider.controller.action.action_impl;

import by.hrachyshkin.provider.model.Account;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseAction{


    protected Integer getAccountId(HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (Integer) session.getAttribute("accountId");
    }

    protected Account.Role getRole(HttpServletRequest request) {

        final HttpSession session = request.getSession(false);
        return (Account.Role) session.getAttribute("accountRole");
    }
}
