package by.hrachyshkin.servlet;

import by.hrachyshkin.entity.Tariff;
import by.hrachyshkin.service.ServiceFactoryImpl;
import by.hrachyshkin.service.ServiceKeys;
import by.hrachyshkin.service.TariffServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tariffs/create")
public class CreateTariffAction extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final TariffServiceImpl tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

        final String name = request.getParameter("name");
        final Tariff.Type type = Tariff.Type.valueOf(request.getParameter("type").toUpperCase());
        final Integer speed = Integer.valueOf(request.getParameter("speed"));
        final Float price = Float.valueOf(request.getParameter("price"));
        tariffService.add(new Tariff(name, type, speed, price));

        response.sendRedirect("/training-java-project-provider/tariffs");
    }
}
