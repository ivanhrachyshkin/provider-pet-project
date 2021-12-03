package by.hrachyshkin.provider.controller.action.impl;

import by.hrachyshkin.provider.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocalizationAction extends BaseAction {

    public static final String LOCALIZATION_ACTION = "/lang";

    @Override
    public String execute(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException, ServiceException {

        if (request.getParameter("cookieLocale") != null) {
            Cookie cookie =new Cookie("lang",
                    request.getParameter("cookieLocale"));
            response.addCookie(cookie);
        }

        return request.getHeader("referer");
    }

    @Override
    public void postExecute(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final String path)
            throws ServletException, IOException, ServiceException {

        response.sendRedirect(path);
    }
}
