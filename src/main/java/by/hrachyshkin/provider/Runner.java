package by.hrachyshkin.provider;

import by.hrachyshkin.provider.service.DiscountServiceImpl;
import by.hrachyshkin.provider.service.ServiceFactoryImpl;
import by.hrachyshkin.provider.service.ServiceKeys;

public class Runner {

    public static void main(String[] args) throws Exception {

        DiscountServiceImpl discountService = ServiceFactoryImpl.getINSTANCE().getService(ServiceKeys.DISCOUNT_SERVICE);
        discountService.find().stream().forEach(System.out::println);
    }
}