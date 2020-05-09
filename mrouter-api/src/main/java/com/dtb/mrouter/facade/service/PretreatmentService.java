package com.dtb.mrouter.facade.service;

import android.content.Context;
import com.dtb.mrouter.facade.Postcard;
import com.dtb.mrouter.facade.template.IProvider;

/**
 * Pretreatment service used for check if need navigation.
 *
 * @author zhilong [Contact me.](mailto:zhilong.liu@aliyun.com)
 * @version 1.0
 * @since 2019-05-08 11:53
 */
public interface PretreatmentService extends IProvider {
    /**
     * Do something before navigation.
     *
     * @param context  context
     * @param postcard meta
     * @return if need navigation.
     */
    boolean onPretreatment(Context context, Postcard postcard);
}
