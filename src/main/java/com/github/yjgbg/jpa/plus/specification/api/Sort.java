package com.github.yjgbg.jpa.plus.specification.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Sort {
    private final List<Order> orders;
    public static final Sort UNSORTED = new Sort(Collections.emptyList());

    public static Sort unsorted() {
        return UNSORTED;
    }


    public boolean isSorted() {
        return orders != null && !orders.isEmpty();
    }
}
