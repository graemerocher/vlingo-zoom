package io.vlingo;

import java.io.Serializable;

/**
 * A {@link Service} is a functional unit that provides a need. Services are immutable and often stateless. Services
 * always consume or produce {@link Aggregate} objects. Services are addressable and discoverable by other services.
 *
 * @author Kenny Bastani
 */
public abstract class Service<T extends Aggregate, ID extends Serializable> {

    public abstract T get(ID id);
    public abstract T create(T entity);
    public abstract void update(T entity);
    public abstract boolean delete(ID id);

    @SuppressWarnings("unchecked")
    public <A extends Action<T>> A getAction(Class<? extends A> clazz) {
        return null;
    }
}
