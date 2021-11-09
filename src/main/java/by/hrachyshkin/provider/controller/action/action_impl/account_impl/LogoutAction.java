package by.hrachyshkin.provider.controller.action.action_impl.account_impl;

import by.hrachyshkin.provider.controller.action.action_impl.BaseAction;
import by.hrachyshkin.provider.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        final HttpSession session = request.getSession(false);
        session.invalidate();

        return "/";
    }
}



