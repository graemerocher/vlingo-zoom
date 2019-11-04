package io.vlingo.config;

import com.google.gson.GsonBuilder;
import io.micronaut.context.ApplicationContext;
import io.micronaut.http.annotation.*;
import io.micronaut.inject.BeanDefinition;
import io.vlingo.http.Response;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.ResourceBuilder;
import io.vlingo.resource.Endpoint;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.vlingo.common.Completes.withFailure;
import static io.vlingo.common.Completes.withSuccess;

public class EndpointConfiguration {

    private final ApplicationContext applicationContext;
    private BeanDefinition<?> controller;
    private ControllerEndpoint endpoint;
    private String baseUri;

    public EndpointConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public BeanDefinition<?> getController() {
        return controller;
    }

    public void setController(BeanDefinition<?> controller) {
        this.controller = controller;
        this.endpoint = new ControllerEndpoint(controller.getName());
        this.baseUri = Objects.requireNonNull(controller.getAnnotation(Controller.class))
                .getValues().get("value").toString();

        controller.getExecutableMethods().forEach(e -> {
            e.getAnnotationMetadata().getDeclaredAnnotationNames().forEach(c -> {
                RequestHandler requestHandler;
                if (Get.class.getName().equals(c)) {
                    requestHandler = ResourceBuilder.get(baseUri + Objects.requireNonNull(e.getAnnotation(Get.class))
                            .getValues().getOrDefault("value", "").toString())
                            .handle(() -> {
                                try {
                                    return withSuccess(Response.of(Response.Status.Ok,
                                            new GsonBuilder()
                                                    .setPrettyPrinting().create().toJson(e.getTargetMethod()
                                                    .invoke(applicationContext.getBean(controller.getBeanType()),
                                                            (Object[]) null))));
                                } catch (IllegalAccessException | InvocationTargetException ex) {
                                    return withFailure(Response.of(Response.Status.InternalServerError));
                                }
                            });
                    endpoint.addRequestHandler(requestHandler);
                } else if (Post.class.getName().equals(c)) {

                } else if (Put.class.getName().equals(c)) {

                } else if (Delete.class.getName().equals(c)) {

                }
            });
        });
    }

    public ControllerEndpoint getEndpoint() {
        return endpoint;
    }

    static class ControllerEndpoint implements Endpoint {

        private final String name;
        private List<RequestHandler> requestHandlers = new ArrayList<>();

        public ControllerEndpoint(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public RequestHandler[] getHandlers() {
            return requestHandlers.toArray(new RequestHandler[0]);
        }

        public void addRequestHandler(RequestHandler handler) {
            requestHandlers.add(handler);
        }
    }
}
