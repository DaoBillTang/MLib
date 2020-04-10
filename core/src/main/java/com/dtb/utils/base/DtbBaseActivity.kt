package com.dtb.utils.base

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import com.dtb.utils.base.contract.HintView
import com.dtb.utils.base.contract.HintViewImpl
import com.dtb.utils.base.contract.ProgressView
import com.dtb.utils.base.contract.ProgressViewImpl
import com.dtb.utils.commons.permissions.PermissionCallbacks
import com.dtb.utils.commons.permissions.PermissionsResult
import com.dtb.utils.commons.statusbar.StatusBarHelper
import com.dtb.utils.commons.utils.screenH
import com.dtb.utils.exlib.R
import com.dtb.utils.rx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Bill on 2016/9/18 11:31.
 * emal:1750352866@qq.com
 * 2018.3.16 添加了 对于软键盘 的监听效果
 */
abstract class DtbBaseActivity :
        AppCompatActivity(),
        LifecycleProvider<ActivityEvent>,
        PermissionCallbacks,
        ProgressView,
        HintView {

    open val progressViewImpl: ProgressViewImpl by lazy {
        ProgressViewImpl(this)
    }

    open val hintViewImpl: HintView by lazy {
        HintViewImpl(this)
    }

    open var mStatusBarHelper: StatusBarHelper? = null

    open val handler: RxHandler by lazy {
        RxHandler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(ActivityEvent.CREATE)
        setContentView(getLayoutResource())
        onTintStatusBar()
        onInitToolBar()
        if (addSoftInputListener()) {
            mKeyboardStateListeners = initInputMethodListener()
            initInputMethod()
        }
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

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP)
        super.onStop()
    }

    protected abstract fun getLayoutResource(): Int
    /********************对状态栏进行修改******************/
    /**
     * 对状态栏进行修改
     */
    open fun onTintStatusBar() {
        if (mStatusBarHelper == null) {
            mStatusBarHelper = StatusBarHelper(this, StatusBarHelper.LEVEL_19_TRANSLUCENT,
                    StatusBarHelper.LEVEL_21_VIEW)
        }
        mStatusBarHelper!!.setColor(resources.getColor(R.color.colorPrimaryDark))
    }
    /********************对toolbar 进行修改******************/
    /**
     * 对toolbar 进行修改
     */
    open fun onInitToolBar() {

    }

    /********************对软键盘进行监听******************/
    open fun addSoftInputListener(): Boolean {
        return false
    }

    interface OnSoftKeyboardStateChangedListener {
        fun onSoftKeyboardStateChanged(isKeyBoardShow: Boolean, keyboardHeight: Int)
    }

    private var mKeyboardStateListeners: OnSoftKeyboardStateChangedListener? = null

    //软键盘状态监听列表
    private var mLayoutChangeListener: ViewTreeObserver.OnGlobalLayoutListener? = null
    var mIsSoftKeyboardShowing: Boolean = false

    open fun initInputMethodListener(): OnSoftKeyboardStateChangedListener? {
        return null
    }

    open fun initInputMethod() {
        mIsSoftKeyboardShowing = false
        mLayoutChangeListener = ViewTreeObserver.OnGlobalLayoutListener {
            //判断窗口可见区域大小
            val r = Rect()
            window.decorView.getWindowVisibleDisplayFrame(r)
            //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
            val heightDifference = screenH() - (r.bottom - r.top)
            val isKeyboardShowing = heightDifference > screenH() / 3
            //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
            if (mIsSoftKeyboardShowing && !isKeyboardShowing || !mIsSoftKeyboardShowing && isKeyboardShowing) {
                mIsSoftKeyboardShowing = isKeyboardShowing
                mKeyboardStateListeners?.onSoftKeyboardStateChanged(mIsSoftKeyboardShowing, heightDifference)
            }
        }
        //注册布局变化监听
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(mLayoutChangeListener)
    }

    override fun showProgressDialog(message: String?) {
        progressViewImpl.showProgressDialog(message)
    }


    override fun showProgressDialog(message: String?, cancel: Boolean) {
        progressViewImpl.showProgressDialog(message, cancel)
    }

    override fun proDialogDismiss() {
        progressViewImpl.proDialogDismiss()
    }


    override fun showErr(msg: String?) {
        hintViewImpl.showErr(msg)
    }

    override fun showErr(msgList: ArrayList<String>?) {
        hintViewImpl.showErr(msgList)
    }

    override fun showErr(msgList: Array<String>?) {
        hintViewImpl.showErr(msgList)
    }


    override fun onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE)
        super.onPause()
        //清理痕迹
        progressViewImpl.proDialogDismiss()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {}

    @SuppressLint("ObsoleteSdkInt")
    override fun onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY)
        //移除布局变化监听
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            window.decorView.viewTreeObserver.removeOnGlobalLayoutListener(mLayoutChangeListener)
        } else {
            window.decorView.viewTreeObserver.removeGlobalOnLayoutListener(mLayoutChangeListener)
        }
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}