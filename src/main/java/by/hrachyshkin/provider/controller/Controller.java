package by.hrachyshkin.provider.controller;

import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.controller.action.action_factory.ActionFactory;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @SneakyThrows
    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {

        final String commandName = request.getRequestURI()
                .substring(request.getContextPath().length());

        final ActionFactory actionFactory = ActionFactory.getINSTANCE();
        final Action command = actionFactory.getCommand(commandName);

        final String path = command.execute(request, response);
        request.getRequestDispatcher(path).forward(request, response);
    }
}
