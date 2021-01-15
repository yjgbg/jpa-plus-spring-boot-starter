package com.github.yjgbg.jpa.plus.specification.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class PageReq {
    private final int page;
    private final int pageSize;
    private final Sort sort;

    public int getOffset() {
        return page * pageSize;
    }
}
