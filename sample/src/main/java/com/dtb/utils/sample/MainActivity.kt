package com.dtb.utils.sample

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.dtb.utils.base.DtbBaseActivity
import com.dtb.utils.commons.logger.Lerror
import com.dtb.utils.commons.logger.Linfo
import com.dtb.utils.commons.timer.CountDownImpl
import com.dtb.utils.commons.timer.contract.CountDownContract
import com.dtb.utils.commons.utils.safe
import com.dtb.utils.ui.MultiStateView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import java.io.Serializable

class MainActivity : DtbBaseActivity(), CountDownContract.View {
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

    fun testIoThread() {
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
//        testRetry()
//        handler.createNetWork<Response<String>>("banner")
//                ?.apply {
//                    observable = ApiService.instance.getObserver()
//                    disposable = object : BaseNoErrorCallSubscriber<String>() {
//                        override fun onSuccess(baseRsps: String?) {
//                            Lerror {
//                                baseRsps ?: "+++++++++++++"
//                            }
//                        }
//
//                        override fun onError(code: Int?, s: ErrorRsps?) {
//                            super.onError(code, s)
//                            Lerror { "error============================================${s?.msg}" }
//                        }
//                    }
//                }?.start()

//        handler.createNetWork<Response<String>>("banner")
//                ?.apply {
//                    observable = ApiService.instance.getObserver()
//                    disposable = object : BaseNoErrorCallSubscriber<String>() {
//                        override fun onSuccess(baseRsps: String?) {
//                            Lerror {
//                                baseRsps ?: "+++++++++++++"
//                            }
//                        }
//                    }
//                }?.start()

        handler.createNetWork<Response<String>> {
            key = "banner2"
            observable = ApiService.instance.getObserver()
            disposable = object : BaseNoErrorCallSubscriber<String>() {
                override fun onSuccess(baseRsps: String?) {
                    Lerror {
                        baseRsps
                                ?: "+++--------===================------------------------++++++++++"
                    }
                    Lerror {
                        "f ienslbtgn vckdobdxfjl"
                    }
                }

                override fun onError(code: Int?, s: ErrorRsps?) {
                    Lerror {
                        "code===$code   \n ${s.toString()}"
                    }
                }
            }
        }

//        testTimer()
//        setLoggerLevel(Log.DEBUG)
//        Linfo { "info=====info====" }
//        Log.d("TAG", "info=====info====")
//        Ldebug { "debug =====================" }
//        Lerror { "error============error==================" }
//
//        safe {
//            val s = "5s" + 1
//            s.toInt()
//        }
//
//        asyncIO {
//            Thread.sleep(5000)
//
//            handler.post {
//                tv.append("这里搞了个 奇怪 东西欧")
//            }
//
//            handler.postDelayed({
//                tv.append("这里搞了个 奇怪 东西欧")
//            }, 1000)
//
//        }
//
//        val u = User()
//        u.name = "tony"
//        u.password = "123456"
//
//        cache {
//            key = "sdf"
//
//            return@cache u
//        }
//
//        Ldebug { s.toString() }
//
//        testTimer()
    }

    private fun testRetry() {
        handler.retry("retry", 5, 1000, {
            val i = (Math.random() * 100)
            Lerror { "i===================$i" }
            i < 80
        }, {
            Lerror { "success" }
        }, {
            Lerror { "error" }
        })
//        handler.removeCallbacksAndMessages("retry")
    }

    private fun testTimer() {
        handler.timer(5, "$i", { timeLimit, key ->
            Lerror {
                "倒计时测试======================\n key =$key\n time ===${5 - timeLimit}"
            }

            safe {
                list[key.toInt()]?.text = "key$key\ntime${15 - timeLimit}"
            }

            if (timeLimit == 3L && key == "3") {
                handler.removeCallbacksAndMessages(key)
            }
        })
    }

    class User : Serializable {
        var name: String? = null
        var password: String? = null
    }
}