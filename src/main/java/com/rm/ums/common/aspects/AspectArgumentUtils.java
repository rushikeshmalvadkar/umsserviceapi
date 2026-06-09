package com.rm.ums.common.aspects;

import java.util.stream.Stream;

public class AspectArgumentUtils {
    public static <T> T findArgument(Object[] args, Class<T> type) {
        return Stream.of(args)
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(type.getSimpleName() + " not found"));
    }
}
