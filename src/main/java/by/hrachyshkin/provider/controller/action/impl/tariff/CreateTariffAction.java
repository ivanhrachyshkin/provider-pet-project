package by.hrachyshkin.provider.controller.action.impl.tariff;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.TariffService;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateTariffAction extends BaseAction {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException, TransactionException {
        try {
            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

            final String name = request.getParameter("name");
            final String type = request.getParameter("type");
            final String speed = request.getParameter("speed");
            final String price = request.getParameter("price");

            tariffService.add(new Tariff(name, Tariff.Type.valueOf(type.toUpperCase()), Integer.valueOf(speed), Float.valueOf(price)));

        } catch (ServiceException | NumberFormatException | NullPointerException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/tariffs";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException, TransactionException {

        response.sendRedirect(request.getContextPath() + "/tariffs");

    }
}
