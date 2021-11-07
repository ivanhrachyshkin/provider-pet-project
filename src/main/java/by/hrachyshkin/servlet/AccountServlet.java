package by.hrachyshkin.servlet;

import by.hrachyshkin.service.ServiceFactoryImpl;
import by.hrachyshkin.service.ServiceKeys;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountServlet extends HttpServlet {

    public AccountServlet() {
        super();
    }

    public void init() throws ServletException {
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().print(ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.ACCOUNT_SERVICE).find());
    }

    public void destroy() {
        super.destroy();
    }
}
