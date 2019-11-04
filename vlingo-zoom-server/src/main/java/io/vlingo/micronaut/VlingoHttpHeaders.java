package io.vlingo.micronaut;

import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.ConversionService;
import io.micronaut.http.MutableHttpHeaders;
import io.vlingo.http.Header;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Delegates to Vlingo's {@link io.vlingo.http.Header.Headers}.
 *
 * @author Graeme Rocher
 * @author Kenny Bastani
 * @since 1.0
 */
@Internal
public class VlingoHttpHeaders implements MutableHttpHeaders {

    Header.Headers<? extends Header> vlingoHeaders;
    final ConversionService<?> conversionService;

    /**
     * @param vlingoHeaders      The Vlingo Http headers
     * @param conversionService The conversion service
     */
    public VlingoHttpHeaders(Header.Headers<?> vlingoHeaders, ConversionService conversionService) {
        this.vlingoHeaders = vlingoHeaders;
        this.conversionService = conversionService;
    }

    /**
     * Default constructor.
     */
    public VlingoHttpHeaders() {
        this.vlingoHeaders = Header.Headers.empty();
        this.conversionService = ConversionService.SHARED;
    }

    /**
     * @return The underlying Vlingo headers.
     */
    public Header.Headers<?> getVlingoHeaders() {
        return vlingoHeaders;
    }

    /**
     * Sets the underlying vlingo headers.
     *
     * @param headers The Vlingo http headers
     */
    void setVlingoHeaders(Header.Headers<?> headers) {
        this.vlingoHeaders = headers;
    }

    @Override
    public <T> Optional<T> get(CharSequence name, ArgumentConversionContext<T> conversionContext) {
        List<String> values = vlingoHeaders.stream().filter(c -> c.name.contentEquals(name))
                .map(c -> c.value)
                .collect(Collectors.toList());
        if (values.size() > 0) {
            if (values.size() == 1 || !isCollectionOrArray(conversionContext.getArgument().getType())) {
                return conversionService.convert(values.get(0), conversionContext);
            } else {
                return conversionService.convert(values, conversionContext);
            }
        }
        return Optional.empty();
    }

    private boolean isCollectionOrArray(Class<?> clazz) {
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public List<String> getAll(CharSequence name) {
        return vlingoHeaders.stream().filter(c -> c.name.contentEquals(name))
                .map(c -> c.value)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> names() {
        return vlingoHeaders.stream()
                .map(c -> c.name)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<List<String>> values() {
        Set<String> names = names();
        List<List<String>> values = new ArrayList<>();
        for (String name : names) {
            values.add(getAll(name));
        }
        return Collections.unmodifiableList(values);
    }

    @Override
    public String get(CharSequence name) {
        return vlingoHeaders.headerOf(name.toString()).value;
    }

    @Override
    public MutableHttpHeaders add(CharSequence header, CharSequence value) {
        vlingoHeaders.and(header.toString(), value.toString());
        return this;
    }

    @Override
    public MutableHttpHeaders remove(CharSequence header) {
        vlingoHeaders.remove(header);
        return this;
    }
}
