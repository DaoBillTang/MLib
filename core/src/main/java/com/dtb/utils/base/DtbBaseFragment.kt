package com.dtb.utils.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dtb.utils.commons.logger.Ldebug
import com.dtb.utils.rx.lifecycle.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Bill on 2016/9/18 11:32.
 * emal:1750352866@qq.com
 */
abstract class DtbBaseFragment : Fragment(), LifecycleProvider<FragmentEvent> {
    private var proDialg: ProgressDialog? = null
    private var isFristVisibile = false

    open val handler: RxHandler by lazy {
        RxHandler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE)
        Ldebug { "onCreate====" }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?
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

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY)
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private val lifecycleSubject = BehaviorSubject.create<FragmentEvent>()

    override fun lifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.hide()
    }

    override fun <T> bindUntilEvent(event: FragmentEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject)
    }

    override fun onAttach(activity: android.app.Activity) {
        super.onAttach(activity)
        lifecycleSubject.onNext(FragmentEvent.ATTACH)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP)
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH)
        super.onDetach()
    }
}