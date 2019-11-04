package io.vlingo;

/**
 * A {@link Module} is a collection of {@link Service} references that are used to consume and/or produce
 * hypermedia URIs.
 *
 * @author Kenny Bastani
 */
public abstract class Module<T extends Aggregate> {

    public abstract Service<? extends Aggregate, ?> getDefaultService();
}
