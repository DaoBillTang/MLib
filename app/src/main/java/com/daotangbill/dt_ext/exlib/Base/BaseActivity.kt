package com.daotangbill.dt_ext.exlib.Base

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.daotangbill.dt_ext.exlib.R
import com.daotangbill.dt_ext.exlib.commons.logger.DtLogger
import com.daotangbill.dt_ext.exlib.commons.statusbar.StatusBarHelper

/**
 * Created by Bill on 2016/9/18 11:31.
 * emal:1750352866@qq.com
 */
abstract class BaseActivity : AppCompatActivity(), DtLogger {
    private var proDialg: ProgressDialog? = null
    private var dlg: AlertDialog? = null
    protected var mStatusBarHelper: StatusBarHelper? = null
    protected val handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
//        startStrictMode()
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResource())
        onTintStatusBar()
    }

    /**
     * 严格模式 仅限测试用
     */
    fun startStrictMode() {
        // 针对线程的相关策略
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectCustomSlowCalls()
//                    .detectNetwork()
                .build())

        // 针对VM的相关策略
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
//                    .detectActivityLeaks()
//                    .detectLeakedSqlLiteObjects()
//                    .detectLeakedClosableObjects()
//                    .penaltyDeath()
                .build())

    }

    protected abstract fun getLayoutResource(): Int

    open fun onTintStatusBar() {
        if (mStatusBarHelper == null) {
            mStatusBarHelper = StatusBarHelper(this, StatusBarHelper.LEVEL_19_TRANSLUCENT,
                    StatusBarHelper.LEVEL_21_VIEW)
        }
        mStatusBarHelper!!.setColor(resources.getColor(R.color.colorPrimaryDark))
    }

    override fun onStart() {
        super.onStart()
    }

    /**
     * 显示[.proDialg],附带文字
     */
    fun showProgressDialog(message: String? = "正在处理中请稍后……") {
        if (proDialg == null) {
            proDialg = ProgressDialog(this)
        }
        proDialg!!.setMessage(message)
        proDialg!!.show()
    }

    fun hideLoading() {
        if (dlg != null) {
            dlg?.dismiss()
            dlg?.cancel()
            dlg = null
        }
    }

    /**
     * 隐藏 progress dialog
     */
    fun proDialogDismiss() {
        if (proDialg != null) proDialg!!.dismiss()
        proDialg = null
    }

    override fun onPause() {
        super.onPause()
        //清理痕迹
        proDialg = null
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}