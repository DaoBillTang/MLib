package com.daotangbill.exlib.base


import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.annotation.CallSuper
import android.support.annotation.CheckResult
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daotangbill.exlib.commons.logger.Ldebug
import com.daotangbill.exlib.rx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Bill on 2016/9/18 11:32.
 * emal:1750352866@qq.com
 */
abstract class DtBaseFragment : Fragment(), LifecycleProvider<FragmentEvent> {
    private var proDialg: ProgressDialog? = null
    private var isFristVisibile = false

    val handler: Handler = Handler(Looper.getMainLooper())

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
        Ldebug { "onCreate====" }
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        Ldebug { "onCreateView====" }
        return super.onCreateView(inflater, container, savedInstanceState)
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

    @JvmOverloads
    fun showProgressDialog(message: String = "正在处理中请稍后……") {
        if (proDialg == null) {
            proDialg = ProgressDialog(this.activity)
        }
        proDialg!!.setMessage(message)
        proDialg!!.show()
    }

    fun proDialogDismiss() {
        if (proDialg != null) proDialg!!.dismiss()
        proDialg = null
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    @CheckResult
    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    @CheckResult
    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    @CheckResult
    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    @CallSuper
    override fun onAttach(activity: android.app.Activity) {
        super.onAttach(activity)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    @CallSuper
    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    @CallSuper
    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    @CallSuper
    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}