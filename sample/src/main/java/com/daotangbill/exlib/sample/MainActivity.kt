package com.daotangbill.exlib.sample

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.daotangbill.exlib.base.DtBaseActivity
import com.daotangbill.exlib.commons.logger.Ldebug
import com.daotangbill.exlib.commons.logger.Lerror
import com.daotangbill.exlib.commons.logger.Linfo
import com.daotangbill.exlib.commons.logger.setLoggerLevel
import com.daotangbill.exlib.commons.timer.CountDownImpl
import com.daotangbill.exlib.commons.timer.contract.CountDownContract
import com.daotangbill.exlib.commons.utils.DateUtil
import com.daotangbill.exlib.commons.utils.asyncIO
import com.daotangbill.exlib.commons.utils.cache
import com.daotangbill.exlib.commons.utils.safe
import com.daotangbill.exlib.ui.MultiStateView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable
import java.util.*

class MainActivity : DtBaseActivity(), CountDownContract.View {
    override fun getLayoutResource(): Int {
        return R.layout.activity_main
    }

    override fun timerChange(timeLimit: Long, key: Int) {
        Lerror {
            "倒计时测试======================\n key =$key\n time ===$timeLimit"
        }

        runOnUiThread {
            list[key]?.text = "key$key\ntime$timeLimit"
        }

        if (timeLimit == 3L) {
            pre.shutdown(this, key)
        }
    }

    private var i = 0

    private val pre by lazy {
        CountDownImpl(6)
    }

    private val list = arrayOfNulls<TextView>(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        show_content.setOnClickListener {
            msv.viewState = MultiStateView.VIEW_STATE_CONTENT
        }

        list[0] = t1
        list[1] = t2
        list[2] = t3
        list[3] = t4
        list[4] = t5
        list[5] = t6

        show_error.setOnClickListener { msv.viewState = MultiStateView.VIEW_STATE_ERROR }
        show_empty.setOnClickListener { msv.viewState = MultiStateView.VIEW_STATE_EMPTY }
        show_load.setOnClickListener { msv.viewState = MultiStateView.VIEW_STATE_LOADING }

        msv.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<View>(R.id.error_tv)
                ?.setOnClickListener {
                    msv.viewState = MultiStateView.VIEW_STATE_CONTENT
                }
        pre.bindView(this)

    }

    fun A() {
        temp.text = "你看报错不？"
        val sb = StringBuilder()
        sb.append("current thread=").append(Thread.currentThread().id)
                .append("\r\n")
                .append("ui thread=")
                .append(Looper.getMainLooper().thread.id)

        Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show()
        Linfo { " sb.toString()" }
    }

    override fun onStart() {
        super.onStart()
        setLoggerLevel(Log.DEBUG)
        Linfo { "info=====info====" }
        Log.d("TAG", "info=====info====")
        Ldebug { "debug =====================" }
        Lerror { "error============error==================" }

        safe {
            val s = "5s" + 1

            s.toInt()
        }

        asyncIO {
            Thread.sleep(5000)

            handler.post {
                tv.append("这里搞了个 奇怪 东西欧")
            }

            handler.postDelayed({
                tv.append("这里搞了个 奇怪 东西欧")
            }, 1000)

        }

        val u = User()
        u.name = "tony"
        u.password = "123456"

        cache {
            key = "sdf"

            return@cache u
        }

        "asd".plus(123)

        val s = DateUtil.addDay(Date(), 15)

        Ldebug { s.toString() }

        B()
    }

    fun B() {
        handler.timer(15, "${i++}", { timeLimit, key ->
            Lerror {
                "倒计时测试======================\n key =$key\n time ===${15 - timeLimit}"
            }

            safe {
                list[key.toInt()]?.text = "key$key\ntime${15 - timeLimit}"
            }

            if (timeLimit == 3L) {
                handler.removeCallbacksAndMessages(key)
            }
        })
    }

    class User : Serializable {
        var name: String? = null
        var password: String? = null

    }
}