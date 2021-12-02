package by.hrachyshkin.provider.controller.action.impl.account;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.controller.action.impl.WelcomeAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends BaseAction {

    public static final String LOGOUT = "/logout";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response) {

        final HttpSession session = request.getSession(false);
        session.invalidate();

        return WelcomeAction.WELCOME;
    }
}



