package io.vlingo.config;

import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Primary;

@ConfigurationProperties(ApplicationConfiguration.PREFIX)
@Primary
@BootstrapContextCompatible
public class ApplicationConfiguration {

    public static final String PREFIX = "application";

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
