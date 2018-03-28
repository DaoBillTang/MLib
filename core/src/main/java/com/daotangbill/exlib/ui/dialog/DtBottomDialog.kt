package com.daotangbill.exlib.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import com.daotangbill.exlib.base.DtBaseDialogFragment
import com.daotangbill.exlib.exlib.R

/**
 * Project com.daotangbill.exlib.ui.dialog
 * Created by DaoTangBill on 2018/3/8/008.
 * Email:tangbaozi@daotangbill.uu.me
 *
 * @author bill
 * @version 1.0
 * @description
 * 在底部显示的 Dialog
 */
class DtBottomDialog : DtBaseDialogFragment() {

    private var isAnimation = false
    private var mTitle: String? = null
    private var mCancel: String? = null
    private var items: Array<String>? = null
    private var mRootView: View? = null
    private var mListener: OnClickListener? = null

    fun setListener(listener: OnClickListener) {
        mListener = listener
    }

    override fun onStart() {
        super.onStart()
        val window: Window? = dialog.window
        var params: WindowManager.LayoutParams? = null
        params = window?.attributes
        params?.gravity = Gravity.BOTTOM
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        window?.attributes = params
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.decorView?.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                dismiss()
            }
            true
        }

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        initData()
        mRootView = inflater.inflate(R.layout.bottom_lib_layout, container, false)
        AnimationUtils.slideToUp(mRootView)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleView = view.findViewById<TextView>(R.id.title)
        if (TextUtils.isEmpty(mTitle)) {
            view.findViewById<View>(R.id.titleLayout).visibility = View.GONE
        } else {
            titleView.text = mTitle
        }

        val listView = view.findViewById<ListView>(R.id.bottom_lib_listView)
        listView.adapter = ArrayAdapter(context, R.layout.bottom_lib_item, R.id.item, items!!)
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (mListener != null) {
                mListener!!.click(position)
            }
            dismiss()
        }

        val cancel = view.findViewById<Button>(R.id.cancel)
        cancel.text = mCancel
        cancel.setOnClickListener { dismiss() }
    }

    private fun initData() {
        mTitle = arguments?.getString("title")
        items = arguments?.getStringArray("items")
        mCancel = arguments?.getString("cancel")
        if (TextUtils.isEmpty(mCancel)) {
            mCancel = resources.getString(R.string.bottom_dialog_lib_cancel)
        }
    }

    override fun dismiss() {
        if (isAnimation) {
            return
        }
        isAnimation = true
        AnimationUtils.slideToDown(mRootView) {
            isAnimation = false
            super@DtBottomDialog.dismissAllowingStateLoss()
        }
    }

    interface OnClickListener {
        /**
         * 点击事件
         *
         * @param position
         */
        fun click(position: Int)
    }

    companion object {

        fun newInstance(titleText: String, items: Array<String>): DtBottomDialog {
            return newInstance(titleText, null, items)
        }

        fun newInstance(titleText: String, cancelText: String?, items: Array<String>): DtBottomDialog {
            val args = Bundle()
            args.putString("title", titleText)
            args.putString("cancel", cancelText)
            args.putStringArray("items", items)
            val fragment = DtBottomDialog()
            fragment.arguments = args
            return fragment
        }
    }
}