package com.github.yjgbg.jpa.plus.specification.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Order {
    private final Direction direction;
    private final String property;

    public boolean isAscending() {
        return direction.isAscending();
    }
}
