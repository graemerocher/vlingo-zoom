package io.vlingo.factory;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.annotation.Controller;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.vlingo.config.EndpointConfiguration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@link ControllerFactory} creates a collection of {@link io.vlingo.resource.Endpoint} beans that will be
 * provided to the {@link io.vlingo.VlingoServer} at application startup. The purpose of this factory is to
 * support the {@link io.micronaut.http.annotation.Controller} annotation.
 *
 * @author Kenny Bastani
 */
@Context
@Factory
public class ControllerFactory {

    private ApplicationContext applicationContext;

    /**
     * Default constructor.
     *
     * @param applicationContext The application context
     */
    public ControllerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        List<EndpointConfiguration> endpoints = applicationContext
                .getBeanDefinitions(Qualifiers.byStereotype(Controller.class))
                .stream()
                .map(c -> {
                    EndpointConfiguration endpointConfiguration = new EndpointConfiguration(applicationContext);
                    endpointConfiguration.setController(c);
                    return endpointConfiguration;
                }).collect(Collectors.toList());
        endpoints.forEach(e -> applicationContext.registerSingleton(e.getEndpoint(), true));
    }
}
