package com.daotangbill.dt_ext.exlib.Base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daotangbill.dt_ext.exlib.commons.logger.DtLogger
import com.daotangbill.dt_ext.exlib.commons.logger.debug

/**
 * Created by Bill on 2016/9/18 11:32.
 * emal:1750352866@qq.com
 */
abstract class BaseFragment : Fragment(), DtLogger {
    private var mAct: Context? = null
    internal var proDialg: ProgressDialog? = null
    private var dlg: AlertDialog? = null
    private val TAG = "baseFragment" + this.javaClass.name
    private var isFristVisibile = false
    val handler: Handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug { "onCreate====" }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        debug { "onCreateView====" }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mAct = context
    }

    /**
     * 设置Fragment是否可见
     * 需要手动使用，或者使用viewpager
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        debug { "setUserVisibleHint() -> isVisibleToUser: " + isVisibleToUser }
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
        debug { "firstInitView=====" }
    }

    open fun onFragmentInvisible() {
        debug { "onFragmentInvisible" }
    }

    open fun onFragmentVisible(view: View) {
        debug { "onFragmentVisible" }
    }

    fun hideLoading() {
        if (dlg != null) {
            dlg!!.dismiss()
            dlg!!.cancel()
            dlg = null
        }
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
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}