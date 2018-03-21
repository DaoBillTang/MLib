package com.daotangbill.exlib.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.daotangbill.exlib.commons.logger.Ldebug
import com.daotangbill.exlib.commons.logger.Lerror
import com.daotangbill.exlib.commons.logger.Linfo
import com.daotangbill.exlib.commons.logger.setLoggerLevel
import com.daotangbill.exlib.ui.MultiStateView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show_content.setOnClickListener {
            msv.viewState = MultiStateView.VIEW_STATE_CONTENT
        }

        show_error.setOnClickListener { msv.viewState = MultiStateView.VIEW_STATE_ERROR }
        show_empty.setOnClickListener { msv.viewState = MultiStateView.VIEW_STATE_EMPTY }
        show_load.setOnClickListener { msv.viewState = MultiStateView.VIEW_STATE_LOADING }

        msv.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<View>(R.id.error_tv)
                ?.setOnClickListener {
                    msv.viewState = MultiStateView.VIEW_STATE_CONTENT
                }

    }

    override fun onStart() {
        super.onStart()
        setLoggerLevel(Log.DEBUG)
        Linfo { "info=====info====" }
        Log.d("TAG", "info=====info====")
        Ldebug { "debug =====================" }
        Lerror { "error============error==================" }
    }

}