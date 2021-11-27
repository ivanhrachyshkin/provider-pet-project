package by.hrachyshkin.provider.controller.action.impl.tariff;

import by.hrachyshkin.provider.controller.action.impl.BaseAction;
import by.hrachyshkin.provider.dao.TransactionException;
import by.hrachyshkin.provider.model.Tariff;
import by.hrachyshkin.provider.service.ServiceException;
import by.hrachyshkin.provider.service.ServiceFactory;
import by.hrachyshkin.provider.service.ServiceKeys;
import by.hrachyshkin.provider.service.TariffService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateTariffAction extends BaseAction {

    public static final String UPDATE_TARIFF = "/tariffs/update";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {

        try {
            checkGetHTTPMethod(request);

            final TariffService tariffService = ServiceFactory.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);

            final Integer id = Integer.valueOf(request.getParameter("tariffId"));
            final String name = request.getParameter("name");
            final Tariff.Type type = Tariff.Type.valueOf(request.getParameter("type").toUpperCase());
            final Integer speed = Integer.valueOf(request.getParameter("speed"));
            final Float price = Float.valueOf(request.getParameter("price"));

            tariffService.update(new Tariff(id, name, type, speed, price));

        } catch (ServiceException | NumberFormatException | TransactionException e) {
            setErrorAttributeToSession(request, e.getMessage());
        }
        return "/tariffs";
    }

    @Override
    public void postExecute(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException, ServiceException {
        response.sendRedirect(request.getContextPath() + path);
    }
}
