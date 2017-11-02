package com.daotangbill.exlib.base;

public interface DtBaseView<T> {
    void setPresenter(T presenter);

    boolean isActive();
}
