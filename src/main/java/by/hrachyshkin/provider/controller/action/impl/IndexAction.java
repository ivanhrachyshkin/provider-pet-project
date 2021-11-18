package by.hrachyshkin.provider.controller.action.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexAction extends BaseAction  {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/login.jsp";
    }
}
