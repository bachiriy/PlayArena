package com.playarena.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Repository<T> {
    Set<T> getAll();
    Optional<T> get(Long id);
    T add(T t);
    void remove(T t);
    void update(T t);
}
