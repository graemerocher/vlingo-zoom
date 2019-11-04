package io.vlingo;

import java.util.Map;
import java.util.function.Consumer;

/**
 * An {@link Action} is a reference of a method. A function contains an address to the location of a method. A function
 * may contain meta-data that describes the inputs and outputs of a method. An action invokes a method annotated with
 * {@link Command}.
 *
 * @author Kenny Bastani
 */
public abstract class Action<A extends Aggregate> {

    protected Consumer<A> onSuccess(Map<String, Object> context) {
        return a -> {};
    }

    protected Consumer<A> onError(Map<String, Object> context) {
        return a -> {};
    }
}
