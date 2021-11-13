package by.hrachyshkin.provider.controller.action.tariff_action;

import by.hrachyshkin.provider.controller.action.BaseAction;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.TariffService;
import by.hrachyshkin.provider.service.impl.TariffServiceImpl;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tariffs")
public class ShowTariffsAction extends BaseAction {

    @SneakyThrows
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       final TariffService tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        final List<Tariff> tariffs;
        final String rawType = request.getParameter("filter");
        if(rawType != null) {
            final Tariff.Type type = Tariff.Type.valueOf(rawType.toUpperCase());
            tariffs = tariffService.findAndFilterByType(type);
        } else tariffs = tariffService.find();
        request.setAttribute("tariffs", tariffs);
        request.getRequestDispatcher("tariffs.jsp").forward(request, response);
    }
}
