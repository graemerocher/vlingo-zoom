package io.examples.account.data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface JpaRepository<ID, T> {

    Optional<T> findById(@NotNull ID id);

    T save(@NotNull T account);

    void deleteById(@NotNull ID id);

    List<T> findAll();

    int update(@NotNull ID id, @NotNull T account);
}
