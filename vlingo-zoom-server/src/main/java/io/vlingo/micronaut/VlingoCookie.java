package io.vlingo.micronaut;

import io.micronaut.core.annotation.Internal;
import io.micronaut.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * A wrapper around a Vlingo cookie.
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@Internal
public class VlingoCookie implements Cookie {

    private final io.netty.handler.codec.http.cookie.Cookie vlingoCookie;

    /**
     * @param vlingoCookie The Vlingo cookie
     */
    public VlingoCookie(io.netty.handler.codec.http.cookie.Cookie vlingoCookie) {
        this.vlingoCookie = vlingoCookie;
    }

    /**
     * @param name  The name
     * @param value The value
     */
    public VlingoCookie(String name, String value) {
        Objects.requireNonNull(name, "Argument name cannot be null");
        Objects.requireNonNull(value, "Argument value cannot be null");

        this.vlingoCookie = new DefaultCookie(name, value);
    }

    /**
     * @return The Vlingo cookie
     */
    public io.netty.handler.codec.http.cookie.Cookie getVlingoCookie() {
        return vlingoCookie;
    }

    @Override
    public @Nonnull
    String getName() {
        return vlingoCookie.name();
    }

    @Override
    public @Nonnull String getValue() {
        return vlingoCookie.value();
    }

    @Override
    public String getDomain() {
        return vlingoCookie.domain();
    }

    @Override
    public String getPath() {
        return vlingoCookie.path();
    }

    @Override
    public boolean isHttpOnly() {
        return vlingoCookie.isHttpOnly();
    }

    @Override
    public boolean isSecure() {
        return vlingoCookie.isSecure();
    }

    @Override
    public long getMaxAge() {
        return vlingoCookie.maxAge();
    }

    @Override
    public @Nonnull Cookie maxAge(long maxAge) {
        vlingoCookie.setMaxAge(maxAge);
        return this;
    }

    @Override
    public @Nonnull Cookie value(@Nonnull String value) {
        vlingoCookie.setValue(value);
        return this;
    }

    @Override
    public @Nonnull Cookie domain(String domain) {
        vlingoCookie.setDomain(domain);
        return this;
    }

    @Override
    public @Nonnull Cookie path(String path) {
        vlingoCookie.setPath(path);
        return this;
    }

    @Override
    public @Nonnull Cookie secure(boolean secure) {
        vlingoCookie.setSecure(secure);
        return this;
    }

    @Override
    public @Nonnull Cookie httpOnly(boolean httpOnly) {
        vlingoCookie.setHttpOnly(httpOnly);
        return this;
    }

    @Override
    public int compareTo(Cookie o) {
        VlingoCookie vlingoCookie = (VlingoCookie) o;
        return vlingoCookie.vlingoCookie.compareTo(this.vlingoCookie);
    }
}
