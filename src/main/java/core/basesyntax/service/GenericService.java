package core.basesyntax.service;

import java.util.List;

public interface GenericService<T, I> {
    T create(T entity);

    T get(I id);

    List<T> getAll();

    T update(T entity);

    boolean delete(I id);
}
