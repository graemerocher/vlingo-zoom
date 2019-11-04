package io.vlingo.micronaut;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.core.convert.value.MutableConvertibleValues;
import io.micronaut.core.convert.value.MutableConvertibleValuesMap;
import io.micronaut.core.io.buffer.ByteBuffer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpHeaders;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.cookie.Cookie;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import io.vlingo.http.Response;
import io.vlingo.http.Version;
import io.vlingo.wire.message.ConsumerByteBuffer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Internal
public class VlingoMutableHttpResponse<B> implements MutableHttpResponse<B> {

    protected Response VlingoResponse;
    final VlingoHttpHeaders headers;
    private final ConversionService conversionService;
    private B body;
    private final Map<Class, Optional> convertedBodies = new LinkedHashMap<>(1);
    private final MutableConvertibleValues<Object> attributes;

    /**
     * @param VlingoResponse    The {@link FullHttpResponse}
     * @param conversionService The conversion service
     */
    @SuppressWarnings("MagicNumber")
    public VlingoMutableHttpResponse(Response VlingoResponse, ConversionService conversionService) {
        this.VlingoResponse = VlingoResponse;
        this.headers = new VlingoHttpHeaders(VlingoResponse.headers, conversionService);
        this.attributes = new MutableConvertibleValuesMap<>(new ConcurrentHashMap<>(4), conversionService);
        this.conversionService = conversionService;
    }

    /**
     * @param conversionService The conversion service
     */
    @SuppressWarnings("MagicNumber")
    public VlingoMutableHttpResponse(ConversionService conversionService) {
        this.VlingoResponse = Response.of(Version.Http1_1, Response.Status.Ok);
        this.headers = new VlingoHttpHeaders(VlingoResponse.headers, conversionService);
        this.attributes = new MutableConvertibleValuesMap<>(new ConcurrentHashMap<>(4), conversionService);
        this.conversionService = conversionService;
    }

    @Override
    public String toString() {
        HttpStatus status = getStatus();
        return status.getCode() + " " + status.getReason();
    }

    @Override
    public Optional<MediaType> getContentType() {
        Optional<MediaType> contentType = MutableHttpResponse.super.getContentType();
        if (contentType.isPresent()) {
            return contentType;
        } else {
            Optional<B> body = getBody();
            if (body.isPresent()) {
                return MediaType.fromType(body.get().getClass());
            }
        }
        return Optional.empty();
    }

    @Override
    public MutableHttpHeaders getHeaders() {
        return headers;
    }

    @Override
    public MutableConvertibleValues<Object> getAttributes() {
        return attributes;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.valueOf(Integer.parseInt(VlingoResponse.status.toString().split("\\s")[0]));
    }

    @Override
    public MutableHttpResponse<B> cookie(Cookie cookie) {
        if (cookie instanceof VlingoCookie) {
            VlingoCookie VlingoCookie = (VlingoCookie) cookie;
            String value = ServerCookieEncoder.LAX.encode(VlingoCookie.getVlingoCookie());
            headers.add(HttpHeaderNames.SET_COOKIE, value);
        } else {
            throw new IllegalArgumentException("Argument is not a Vlingo compatible Cookie");
        }
        return this;
    }

    @Override
    public Optional<B> getBody() {
        return Optional.ofNullable(body);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> Optional<T1> getBody(Class<T1> type) {
        return getBody(Argument.of(type));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> getBody(Argument<T> type) {
        return convertedBodies.computeIfAbsent(type.getType(), aClass -> getBody().flatMap(b -> {
            ArgumentConversionContext<T> context = ConversionContext.of(type);
            if (b instanceof ByteBuffer) {
                return conversionService.convert(((ByteBuffer) b).asNativeBuffer(), context);
            }
            return conversionService.convert(b, context);
        }));
    }

    @Override
    public MutableHttpResponse<B> status(HttpStatus status, CharSequence message) {
        message = message == null ? status.getReason() : message;
        VlingoResponse = Response.of(Stream.of(Response.Status.values()).parallel().filter(s -> s.toString()
                .contains(String.valueOf(status.getCode()))).findFirst()
                .orElseThrow(() -> new RuntimeException("Could not parse status code")), message.toString());
        return this;
    }

    /**
     * @return The Vlingo {@link FullHttpResponse}
     */
    public Response getNativeResponse() {
        return VlingoResponse;
    }

    @Override
    public VlingoMutableHttpResponse<B> body(B body) {
        this.body = body;
        if (body instanceof ConsumerByteBuffer) {
            replace((ConsumerByteBuffer) body);
        }
        return this;
    }

    /**
     * @param body The body to replace
     * @return The current instance
     */
    public VlingoMutableHttpResponse replace(ConsumerByteBuffer body) {
        this.VlingoResponse = Response.of(VlingoResponse.status, VlingoResponse.headers, body.array());
        this.headers.setVlingoHeaders(this.VlingoResponse.headers);
        return this;
    }

}
