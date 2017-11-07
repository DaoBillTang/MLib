package com.daotangbill.exlib.ui.rollviewpager.hintview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.daotangbill.exlib.ui.rollviewpager.Util;


/**
 * Project hschefu-android
 * Created by BILL on 2016/1/10.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 */
public class ColorPointHintView extends ShapeHintView {
    private int focusColor;
    private int normalColor;

    public ColorPointHintView(Context context, int focusColor, int normalColor) {
        super(context);
        this.focusColor = focusColor;
        this.normalColor = normalColor;
    }

    @Override
    public Drawable makeFocusDrawable() {
        GradientDrawable dot_focus = new GradientDrawable();
        dot_focus.setColor(focusColor);
        dot_focus.setCornerRadius(Util.dip2px(getContext(), 4));
        dot_focus.setSize(Util.dip2px(getContext(), 8), Util.dip2px(getContext(), 8));
        return dot_focus;
    }

    @Override
    public Drawable makeNormalDrawable() {
        GradientDrawable dot_normal = new GradientDrawable();
        dot_normal.setColor(normalColor);
        dot_normal.setCornerRadius(Util.dip2px(getContext(), 4));
        dot_normal.setSize(Util.dip2px(getContext(), 8), Util.dip2px(getContext(), 8));
        return dot_normal;
    }
}
