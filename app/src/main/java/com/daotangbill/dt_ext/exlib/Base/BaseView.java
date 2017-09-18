package com.daotangbill.dt_ext.exlib.Base;

public interface BaseView<T> {
    void setPresenter(T presenter);

    boolean isActive();
}
