package com.github.yjgbg.jpa.plus.utils;

import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.persistence.criteria.Path;
import java.util.Arrays;

public class PathUtils {
    @NotNull
    @Contract(pure = true)
    public static <P> Path<P> str2Path(@NotNull Path<?> root, @NotNull String string) {
        @SuppressWarnings("unchecked")
        val path = (Path<P>) root;
        return Arrays.stream(string.split("\\."))
                .reduce(path, Path::get, (a, b) -> a);
    }
}
