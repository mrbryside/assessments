package com.kbtg.bootcamp.posttest.shared.composer;

import lombok.AllArgsConstructor;

import java.util.function.Function;

@AllArgsConstructor
public class Composer<T> {
    private T value;

    public static <T> Composer<T> of(T value) {
        return new Composer<>(value);
    }

    public <R> Composer<R> bind(Function<T, R> function) {
        return new Composer<>(function.apply(value));
    }

    public T get() {
        return value;
    }
}


