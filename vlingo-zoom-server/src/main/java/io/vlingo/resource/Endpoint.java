package io.vlingo.resource;

import com.google.gson.GsonBuilder;
import io.vlingo.common.Completes;
import io.vlingo.error.ErrorInfo;
import io.vlingo.http.Header;
import io.vlingo.http.Response;
import io.vlingo.http.ResponseHeader;
import io.vlingo.http.media.ContentMediaType;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.Resource;
import io.vlingo.http.resource.ResourceBuilder;

import java.util.function.Supplier;

import static io.vlingo.common.Completes.withSuccess;
import static io.vlingo.http.Response.Status.BadRequest;
import static io.vlingo.http.Response.of;

public interface Endpoint {
    String getName();

    RequestHandler[] getHandlers();

    default Resource getResource() {
        assert getName() != null;
        assert getHandlers() != null;
        return ResourceBuilder.resource(getName(), getHandlers());
    }

    default Completes<Response> getResponse(Response.Status status, Supplier<?> handle) {
        String body = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create()
                .toJson(handle.get());
        Header.Headers<ResponseHeader> headers =
                Header.Headers.of(ResponseHeader.contentType(ContentMediaType.Json().toString()));
        Response response = of(status, headers, body);
        return withSuccess(response);
    }

    default Completes<Response> getResponse(Response.Status status, Procedure handle) {
        handle.invoke();
        Header.Headers<ResponseHeader> headers =
                Header.Headers.of(ResponseHeader.contentType(ContentMediaType.Json().toString()));
        Response response = of(status, headers);
        return withSuccess(response);
    }

    default Response getErrorResponse(Throwable error) {
        return getResponse(BadRequest, () -> new ErrorInfo(error)).outcome();
    }
}
