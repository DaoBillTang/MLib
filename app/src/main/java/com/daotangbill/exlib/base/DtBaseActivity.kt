package com.daotangbill.exlib.base

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v7.app.AppCompatActivity
import com.daotangbill.exlib.commons.logger.DtLogger
import com.daotangbill.exlib.commons.statusbar.StatusBarHelper
import com.daotangbill.exlib.exlib.R
import com.daotangbill.exlib.rx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Bill on 2016/9/18 11:31.
 * emal:1750352866@qq.com
 */
abstract class DtBaseActivity : AppCompatActivity(), DtLogger, LifecycleProvider<ActivityEvent> {
    private var proDialg: ProgressDialog? = null
    private var mStatusBarHelper: StatusBarHelper? = null
    val handler: Handler = Handler(Looper.getMainLooper())

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        setContentView(getLayoutResource())
        onTintStatusBar()
    }

    private val lifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    @CheckResult
    override fun lifecycle(): Observable<ActivityEvent> = lifecycleSubject.hide()

    @CheckResult
    override fun <T> bindUntilEvent(event: ActivityEvent): LifecycleTransformer<T> =
            RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> =
            RxLifecycleAndroid.bindActivity(lifecycleSubject)

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }


    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
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


    /**
     * 隐藏 progress dialog
     */
    fun proDialogDismiss() {
        if (proDialg != null) proDialg!!.dismiss()
        proDialg = null
    }

    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
        //清理痕迹
        proDialg = null
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}