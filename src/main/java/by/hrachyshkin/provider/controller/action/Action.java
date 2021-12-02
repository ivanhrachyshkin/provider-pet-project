package by.hrachyshkin.provider.controller.action;

import by.hrachyshkin.provider.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Action {

    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ServiceException;

    void postExecute(HttpServletRequest request,
                     HttpServletResponse response,
                     String path)
            throws ServletException, IOException, ServiceException;
}
