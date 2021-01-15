package com.github.yjgbg.jpa.plus.specification.api;

public enum Direction {
    ASC, DESC;

    public boolean isAscending() {
        return this == ASC;
    }
}
