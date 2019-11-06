package io.vlingo.resource;

import com.google.gson.GsonBuilder;
import io.vlingo.VlingoServer;
import io.vlingo.common.Completes;
import io.vlingo.error.ErrorInfo;
import io.vlingo.http.Header;
import io.vlingo.http.Response;
import io.vlingo.http.ResponseHeader;
import io.vlingo.http.media.ContentMediaType;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.Resource;
import io.vlingo.http.resource.ResourceBuilder;

import java.util.Collections;
import java.util.function.Supplier;

import static io.vlingo.common.Completes.withSuccess;
import static io.vlingo.http.Response.Status.BadRequest;
import static io.vlingo.http.Response.of;

/**
 * The {@link Endpoint} interface provides a way to implement an endpoint definition that can be annotated with
 * {@link io.vlingo.annotations.Resource} and mounted to an embeddable {@link VlingoServer}. Methods contained
 * in this interface provide default configurations for how {@link VlingoServer} serializes HTTP responses.
 * <p>
 * An {@link Endpoint} is a versioned definition of a set of request/response handlers that forms an anti-corruption
 * layer between HTTP API consumers and your internal services. An endpoint can be used to route requests to different
 * versions of your internal services.
 *
 */
public interface Endpoint {
    String getName();

    String getVersion();

    RequestHandler[] getHandlers();

    default Resource getResource() {
        assert getName() != null;
        assert getHandlers() != null;
        return ResourceBuilder.resource(getName() + " Endpoint Resource v" + getVersion(), getHandlers());
    }

    default <T> Completes<Response> getResponse(Response.Status status, Supplier<T> handle) {
        String body = serialize(handle);
        Header.Headers<ResponseHeader> headers = Header.Headers.of(getContentTypeResponseHeader());
        Response response = of(status, headers, body);
        return withSuccess(response);
    }

    default Completes<Response> getResponse(Response.Status status, Procedure handle) {
        handle.invoke();
        Header.Headers<ResponseHeader> headers = Header.Headers.of(getContentTypeResponseHeader());
        Response response = of(status, headers);
        return withSuccess(response);
    }

    default Response getErrorResponse(Throwable error) {
        return getResponse(BadRequest, () -> new ErrorInfo(error)).outcome();
    }

    default ResponseHeader getContentTypeResponseHeader() {
        return ResponseHeader.contentType(new ContentMediaType("application",
                "vnd." + getName().toLowerCase() + "+json",
                Collections.singletonMap("version", getVersion())).toString());
    }

    default String getSerializedDateFormat() {
        return "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    }

    default <T> String serialize(Supplier<T> handle) {
        return setPrettyPrinting(new GsonBuilder())
                .setDateFormat(getSerializedDateFormat())
                .create()
                .toJson(handle.get());
    }

    default GsonBuilder setPrettyPrinting(GsonBuilder gsonBuilder) {
        return gsonBuilder.setPrettyPrinting();
    }
}
