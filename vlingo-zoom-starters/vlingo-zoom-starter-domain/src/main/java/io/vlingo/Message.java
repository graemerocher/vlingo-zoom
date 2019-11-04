package io.vlingo;

/**
 * The {@link Message} interface describes a container for an event that mutates the state of the {@link T} aggregate.
 *
 * @param <T>
 * @author Kenny Bastani
 */
public interface Message<T extends Aggregate> {

    /**
     * Sets the {@link Aggregate} that is affected by this {@link Message}.
     *
     * @param aggregate
     */
    void setAggregate(T aggregate);
}
