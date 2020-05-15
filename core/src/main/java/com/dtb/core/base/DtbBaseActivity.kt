package com.dtb.core.base

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.dtb.core.base.contract.HintView
import com.dtb.core.base.contract.HintViewImpl
import com.dtb.core.base.contract.ProgressView
import com.dtb.core.base.contract.ProgressViewImpl
import com.dtb.core.common.permissions.PermissionCallbacks
import com.dtb.core.common.permissions.PermissionsResult
import com.dtb.core.common.utils.screenH
import com.dtb.core.rx.lifecycle.LifecycleEventSubject
import com.dtb.core.rx.lifecycle.LifecycleProvider
import com.dtb.core.rx.lifecycle.LifecycleTransformer
import io.reactivex.Observable

/**
 * Created by Bill on 2016/9/18 11:31.
 * emal:1750352866@qq.com
 * 2018.3.16 添加了 对于软键盘 的监听效果
 */
abstract class DtbBaseActivity :
    AppCompatActivity(),
    LifecycleProvider<Lifecycle.Event>,
    PermissionCallbacks,
    ProgressView,
    HintView {

    open val progressViewImpl: ProgressViewImpl by lazy {
        val v = ProgressViewImpl(this)
        lifecycle.addObserver(v)
        return@lazy v
    }

    open val hintViewImpl: HintView by lazy {
        HintViewImpl(this)
    }

    open val handler: RxHandler by lazy {
        RxHandler()
    }

    private val lifecycleSubject = LifecycleEventSubject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObserver()
        onInitContentView()
        onTintStatusBar()
        onInitToolBar()
        if (addSoftInputListener()) {
            mKeyboardStateListeners = initInputMethodListener()
            initInputMethod()
        }
    }

    open fun addObserver() {
        lifecycle.addObserver(lifecycleSubject)
    }

    abstract fun onInitContentView()

    @CheckResult
    override fun lifecycle(): Observable<Lifecycle.Event> = lifecycleSubject.lifecycle()

    @CheckResult
    override fun <T> bindUntilEvent(event: Lifecycle.Event): LifecycleTransformer<T> =
        lifecycleSubject.bindUntilEvent(event)

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> = lifecycleSubject.bindToLifecycle()

    /********************对状态栏进行修改******************/
    /**
     * 对状态栏进行修改
     */
    open fun onTintStatusBar() {

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
                mKeyboardStateListeners?.onSoftKeyboardStateChanged(
                    mIsSoftKeyboardShowing,
                    heightDifference
                )
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
        super.onPause()
        //清理痕迹
        progressViewImpl.proDialogDismiss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {}


    @SuppressLint("ObsoleteSdkInt")
    override fun onDestroy() {
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