package by.hrachyshkin.provider;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleFactory {

    @Getter
    private static final ResourceBundleFactory INSTANCE = new ResourceBundleFactory();

    @Getter
    private final ResourceBundle rb = ResourceBundle
            .getBundle("langs", new Locale("en", "US"));
}
