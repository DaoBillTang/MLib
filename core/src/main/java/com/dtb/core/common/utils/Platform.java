package com.dtb.core.common.utils;

import android.os.Build;

/**
 * @author JoongWon Baik
 * 版本号的 判断
 */
public class Platform {
    /**
     * 4.0
     * @return
     */
    public static boolean hasICS() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * 4.4
     *
     * @return
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }
}
