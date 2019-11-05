package io.examples.account.data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * This interface describes a generic {@link JpaRepository} abstraction for managing RDBMS data using Hibernate/JPA.
 *
 * @param <ID> is the entity ID type.
 * @param <T>  is the entity type.
 * @author Kenny Bastani
 */
public interface JpaRepository<ID, T> {

    /**
     * Find an entity by its {@link ID}.
     *
     * @param id is the {@link ID} of the entity to retrieve.
     * @return the entity {@link T} or returns null.
     */
    Optional<T> findById(@NotNull ID id);

    /**
     * Saves the entity of type {@link T}.
     *
     * @param entity is the entity of type {@link T} to save.
     * @return the saved entity of type {@link T}.
     */
    T save(@NotNull T entity);

    /**
     * Deletes an entity of type {@link T} with the unique identifier of type {@link ID}.
     *
     * @param id is the entity identifier of type {@link ID}.
     */
    void deleteById(@NotNull ID id);

    /**
     * Returns a list of all entities of type {@link T}.
     *
     * @return a list of entities of type {@link T}.
     */
    List<T> findAll();

    /**
     * Updates fields belonging to the entity of type {@link T}.
     *
     * @param id     is the entity identifier of type {@link ID} to lookup before updating any its fields.
     * @param entity is the entity model containing the fields other than its identifier that will be updated.
     * @return the number of fields that were successfully updated on the entity of type {@link T}.
     */
    int update(@NotNull ID id, @NotNull T entity);
}
