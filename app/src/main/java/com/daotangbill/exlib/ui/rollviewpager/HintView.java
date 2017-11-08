package com.daotangbill.exlib.ui.rollviewpager;


/**
 * @author bill
 *         created at 2017/11/7 11:05
 * @description　指示器 需要实现的部分
 */
public interface HintView {

    /**
     * @author bill
     * created at 2017/11/7 11:05
     * @description
     */
    void initView(int length, int gravity);

    /**
     * @author bill
     * created at 2017/11/7 11:05
     * @description
     */
    void setCurrent(int current);
}

