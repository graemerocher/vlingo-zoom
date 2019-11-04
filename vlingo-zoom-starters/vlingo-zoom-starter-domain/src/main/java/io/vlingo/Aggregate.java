package io.vlingo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link Aggregate} is an entity that contains references to one or more other {@link Entity} objects. Aggregates
 * may contain a collection of references to a {@link Command}. All command references on an aggregate should be
 * explicitly typed.
 *
 * @author Kenny Bastani
 */
public abstract class Aggregate<E extends Message, ID extends Serializable> {

    public abstract ID getId();

    /**
     * Retrieves an {@link Action} for this {@link Module}
     *
     * @return the action for this provider
     * @throws IllegalArgumentException if the application context is unavailable or the provider does not exist
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    protected <T extends Action<A>, A extends Aggregate> T getAction(Class<T> actionType)
            throws IllegalArgumentException {
        Module provider = getModule();
        Service service = provider.getDefaultService();
        return (T) service.getAction(actionType);
    }

    /**
     * Retrieves an instance of the {@link Module} for this instance
     *
     * @return the provider for this instance
     * @throws IllegalArgumentException if the application context is unavailable or the provider does not exist
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    public <T extends Module<A>, A extends Aggregate<E, ID>> T getModule() throws IllegalArgumentException {
        return null;
    }

    /**
     * Retrieves an instance of a {@link Module} with the supplied type
     *
     * @return an instance of the requested {@link Module}
     * @throws IllegalArgumentException if the application context is unavailable or the provider does not exist
     */
    @JsonIgnore
    public <T extends Module<A>, A extends Aggregate<E, ID>> T getModule(Class<T> providerType) throws
            IllegalArgumentException {
        return (T) null;
    }

    @JsonIgnore
    public abstract List<E> getEvents();

    /**
     * Append a new {@link Message} to the {@link Aggregate} reference for the supplied identifier.
     *
     * @param event is the {@link Message} to append to the {@link Aggregate} entity
     * @return the newly appended {@link Message}
     */

    public abstract E sendEvent(E event);

    /**
     * Append a new {@link Message} to the {@link Aggregate} reference for the supplied identifier.
     *
     * @param event is the {@link Message} to append to the {@link Aggregate} entity
     * @return the newly appended {@link Message}
     */
    public abstract boolean sendAsyncEvent(E event);

    @SuppressWarnings("unchecked")
    public E appendEvent(E event) {
        event.setAggregate(this);
        getEvents().add(event);
        getEntityService().update(this);
        return event;
    }

    public List<Method> getCommands() {
        // Get command annotations on the aggregate
        List<Command> commands = Arrays.stream(this.getClass()
                .getMethods())
                .filter(a -> a.isAnnotationPresent(Command.class))
                .map(a -> a.getAnnotation(Command.class))
                .collect(Collectors.toList());

        return null;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    protected Service<Aggregate<E, ID>, ID> getEntityService() {
        return (Service<Aggregate<E, ID>, ID>) getModule().getDefaultService();
    }
}
