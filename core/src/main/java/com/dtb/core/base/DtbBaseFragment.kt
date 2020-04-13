package com.dtb.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.dtb.core.base.contract.HintView
import com.dtb.core.base.contract.HintViewImpl
import com.dtb.core.base.contract.ProgressView
import com.dtb.core.base.contract.ProgressViewImpl
import com.dtb.core.common.logger.Ldebug
import com.dtb.core.rx.lifecycle.LifecycleEventSubject
import com.dtb.core.rx.lifecycle.LifecycleProvider
import com.dtb.core.rx.lifecycle.LifecycleTransformer
import io.reactivex.Observable

/**
 * Created by Bill on 2016/9/18 11:32.
 * email:1750352866@qq.com
 */
abstract class DtbBaseFragment :
    Fragment(),
    LifecycleProvider<Lifecycle.Event>,
    ProgressView,
    HintView {

    open val progressViewImpl: ProgressView by lazy {
        val v = ProgressViewImpl(this.activity)
        lifecycle.addObserver(v)
        return@lazy v
    }

    open val hintViewImpl: HintView by lazy {
        HintViewImpl(this.activity)
    }

    private var isFristVisibile = false

    open val handler: RxHandler by lazy {
        RxHandler()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?
        , savedInstanceState: Bundle?
    ): View? {
        Ldebug { "onCreateView====" }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObserver()
    }

    open fun addObserver() {
        lifecycle.addObserver(lifecycleSubject)

    }

    /**
     * 设置Fragment是否可见
     * 需要手动使用，或者使用viewpager
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Ldebug { "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser }
        if (view != null) {
            if (isVisibleToUser && !isFristVisibile) {
                firstInitView(view!!)
                onFragmentVisible(view!!)
                return
            } else if (isVisibleToUser) {
                onFragmentVisible(view!!)
            } else {
                onFragmentInvisible()
            }
        }
    }

    open fun firstInitView(view: View) {
        Ldebug { "firstInitView=====" }
    }

    open fun onFragmentInvisible() {
        Ldebug { "onFragmentInvisible" }
    }

    open fun onFragmentVisible(view: View) {
        Ldebug { "onFragmentVisible" }
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

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private val lifecycleSubject = LifecycleEventSubject()

    @CheckResult
    override fun lifecycle(): Observable<Lifecycle.Event> = lifecycleSubject.lifecycle()

    @CheckResult
    override fun <T> bindUntilEvent(event: Lifecycle.Event): LifecycleTransformer<T> =
        lifecycleSubject.bindUntilEvent(event)

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> = lifecycleSubject.bindToLifecycle()
}