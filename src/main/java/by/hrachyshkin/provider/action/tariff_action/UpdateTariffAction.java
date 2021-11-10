package by.hrachyshkin.provider.action.tariff_action;

import by.hrachyshkin.provider.entity.Tariff;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tariffs/update")
public class UpdateTariffAction extends HttpServlet {

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

        final Integer id = Integer.valueOf(request.getParameter("id"));
        final String name = request.getParameter("name");
        final Tariff.Type type = Tariff.Type.valueOf(request.getParameter("type").toUpperCase());
        final Integer speed = Integer.valueOf(request.getParameter("dateFrom"));
        final Float price = Float.valueOf(request.getParameter("dateTo"));
        tariffService.update(new Tariff(id, name, type, speed, price));

        response.sendRedirect("/training-java-project-provider/tariffs");
    }
}
