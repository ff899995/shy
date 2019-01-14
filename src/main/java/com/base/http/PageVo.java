package com.base.http;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageVo<T> {

    private Integer allcount;

    private T page;
}
