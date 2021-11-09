package by.hrachyshkin.provider.controller.action.action_impl;

import by.hrachyshkin.provider.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        return "/main.jsp";
    }
}
