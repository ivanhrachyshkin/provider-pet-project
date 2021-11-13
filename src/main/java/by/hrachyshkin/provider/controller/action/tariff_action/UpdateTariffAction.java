package by.hrachyshkin.provider.controller.action.tariff_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffService;
import by.hrachyshkin.provider.service.impl.TariffServiceImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tariffs/update")
public class UpdateTariffAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("tariffId"));
            final String name = request.getParameter("name");
            final Tariff.Type type = Tariff.Type.valueOf(request.getParameter("type").toUpperCase());
            final Integer speed = Integer.valueOf(request.getParameter("speed"));
            final Float price = Float.valueOf(request.getParameter("price"));

            tariffService.update(new Tariff(id, name, type, speed, price));

        } catch (ServiceException | NumberFormatException e) {
            request.setAttribute("error", e.getMessage());
        }
        request.getRequestDispatcher("/tariffs").forward(request, response);
    }
}
