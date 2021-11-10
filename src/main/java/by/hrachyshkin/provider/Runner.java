package by.hrachyshkin.provider;

import by.hrachyshkin.provider.entity.Tariff;
import by.hrachyshkin.provider.service.TariffServiceImpl;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

public class Runner {

    public static void main(String[] args) throws Exception {

        TariffServiceImpl tariffService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.TARIFF_SERVICE);
        tariffService.add(new Tariff("name", Tariff.Type.UNLIMITED, 100, 50.0f));
    }
}