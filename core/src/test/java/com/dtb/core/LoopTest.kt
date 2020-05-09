package com.dtb.core

import android.graphics.Bitmap
import android.os.*
import android.util.Log

/**
 * project frame
 * Created by dtb on 2020/4/13
 * email: 1750352866@qq.com
 *
 * @author: dtb
 * *
 * @version: 1.0
 * *
 * @description:
 */
class LoopTest {

    class MyThread : Thread() {
        var looper: Looper? = null

        override fun run() {
            Looper.prepare();//创建该子线程的Looper
            looper = Looper.myLooper();//取出该子线程的Looper
            Looper.loop();//只要调用了该方法才能不断循环取出消息
        }
    }

    class MyHandlerThread(name: String) : HandlerThread(name) {
        override fun run() {
            super.run()


        }
    }

    private var handler: Handler? = null


    private fun send() {
        val thread = MyThread()
        thread.start()//千万不要忘记开启这个线程
        // 可能报空，因为 thread 的looper 初始化还没完成
        //        可以选用 HandlerThread
        handler = object : Handler(thread.looper) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d("xxx", "线程为：${Thread.currentThread()}")
            }
        }
    }
}