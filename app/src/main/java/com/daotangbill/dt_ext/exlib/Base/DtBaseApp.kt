package com.daotangbill.dt_ext.exlib.Base

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.daotangbill.dt_ext.exlib.commons.logger.DtLogger

/**
 * project com.daotangbill.dt_ext.exlib.Base
 * Created by Bill on 2017/9/18.
 * emal: tangbakzi@daotangbill.uu.me
 * @author: Bill
 * @version: 1.0
 * @description:
 */
abstract class DtBaseApp : Application(), DtLogger {

    private var life: LifeListener? = null

    companion object {
        @JvmStatic
        var act: Activity? = null
        var actList: MutableList<Activity> = mutableListOf()
    }

    override fun onCreate() {
        super.onCreate()
        life = LifeListener()
        registerActivityLifecycleCallbacks(life)
        val crash = CrashHandler.getsInstance()
        crash.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(life)
    }


    /**
     * 去市场下载页面
     * com.tencent.android.qqdownloader 腾讯应用宝
     * com.qihoo.appstore 360手机助手
     * com.baidu.appsearch 百度手机助手
     * com.xiaomi.market 小米应用商店
     * com.huawei.appmarket 华为应用商店
     * com.wandoujia.phoenix2 豌豆荚
     * com.dragon.android.pandaspace 91手机助手
     * com.hiapk.marketpho 安智应用商店
     * com.yingyonghui.market 应用汇
     * com.tencent.qqpimsecure QQ手机管家
     * com.mappn.gfan 机锋应用市场
     * com.pp.assistant PP手机助手
     * com.oppo.market OPPO应用商店
     * cn.goapk.market GO市场
     * zte.com.market 中兴应用商店
     * com.yulong.android.coolmart 宇龙Coolpad应用商店
     * com.lenovo.leos.appstore 联想应用商店
     * com.coolapk.market cool市场
     */
    fun goToMarket(context: Context?, packageName: String) {
        if (context == null) return
        val uri = Uri.parse("market://details?id=" + packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    fun exit() {
        actList.forEach {
            it.finish()
        }
    }

    fun finish(actName: String) {
        actList.forEach {
            if (it::class.java.name == actName) {
                it.finish()
            }
        }
    }

    class LifeListener : ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
            act = activity
        }

        override fun onActivityDestroyed(activity: Activity?) {
            if (activity != null) {
                actList.remove(activity)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            if (activity != null) {
                actList.add(activity)
            }
        }
    }
}