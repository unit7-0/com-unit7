package com.unit7.study.translationmethods.labs.chains;

import java.util.List;

public interface Builder<T> {
    List<T> build(Container container, Object... args);
}
