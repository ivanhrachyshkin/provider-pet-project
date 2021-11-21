package by.hrachyshkin.provider.controller.action.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageAction extends BaseAction {

    public static final String MAIN = "/main";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        return "/main.jsp";
    }
}
