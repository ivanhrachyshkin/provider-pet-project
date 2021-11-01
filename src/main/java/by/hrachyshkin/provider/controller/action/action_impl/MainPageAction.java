package by.hrachyshkin.provider.controller.action.action_impl;

import by.hrachyshkin.provider.controller.action.Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainPageAction extends BaseAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){

        //  if (getAccountId(request) == null
        //          && getRole(request)== null) {
        //      request.getRequestDispatcher("/login.jsp").forward(request, response);
        //  }

        return "/main.jsp";
    }
}
