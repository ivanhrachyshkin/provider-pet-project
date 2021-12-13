package by.hrachyshkin.provider.controller;

import by.hrachyshkin.provider.controller.action.Action;
import by.hrachyshkin.provider.controller.action.ActionFactory;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Controller extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)  {
        service(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)  {

        service(request, response);
    }

    @SneakyThrows
    @Override
    protected void service(final HttpServletRequest request,
                           final HttpServletResponse response) {

        final String commandName = request.getServletPath();

        final ActionFactory actionFactory = ActionFactory.getINSTANCE();
        final Action command = actionFactory.getCommand(commandName);

        final String path = command.execute(request, response);
        command.postExecute(request, response, path);
    }
}
