package com.dtb.router.core

import android.content.Context
import android.util.LruCache
import com.dtb.router.facade.annotation.Route
import com.dtb.router.facade.service.AutowiredService
import com.dtb.router.facade.template.ISyringe
import com.dtb.router.utils.Consts
import java.util.*

/**
 * Autowired service impl.
 *
 * @author zhilong [Contact me.](mailto:zhilong.lzl@alibaba-inc.com)
 * @version 1.0
 * @since 2017/2/28 下午6:08
 */
@Route(path = "/router/service/autowired")
class AutowiredServiceImpl : AutowiredService {
    private var classCache: LruCache<String, ISyringe?>? = null
    private var blackList: MutableList<String>? = null

    override fun init(context: Context) {
        classCache = LruCache(66)
        blackList = ArrayList()
    }

    override fun autowire(instance: Any) {
        val className = instance.javaClass.name
        try {
            if (blackList?.contains(className) == false) {
                var autowiredHelper = classCache!![className]
                if (null == autowiredHelper) {  // No cache.
                    autowiredHelper =
                        Class.forName(instance.javaClass.name + Consts.SUFFIX_AUTOWIRED)
                            .getConstructor().newInstance() as ISyringe
                }
                autowiredHelper.inject(instance)
                classCache!!.put(className, autowiredHelper)
            }
        } catch (ex: Exception) {
            blackList!!.add(className) // This instance need not autowired.
        }
    }
}