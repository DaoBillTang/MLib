package com.daotangbill.exlib.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.SystemClock
import com.daotangbill.exlib.commons.logger.Lwarn
import com.daotangbill.exlib.commons.utils.showResultDialog
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

/**
 * project com.hyhs.hschefu.staff.base
 * Created by Bill on 2017/7/11.
 * emal: tangbakzi@daotangbill.uu.me

 * @author: Bill
 * *
 * @version: 1.0
 * *
 * @description: 全局异常捕捉器
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    private var mUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
    private var mContext: Context by Delegates.notNull<Context>()

    /**
     * 初始化操作
     * @param context
     */
    fun init(context: Context) {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        mContext = context.applicationContext
    }

    /**
     * 这个方法是最为关键的，当程序中未有被捕获的异常的时候，会调用 uncaughtException
     * @param thread    未捕获异常的线程
     *
     * @param throwable 未捕获的异常
     */
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        //将捕获的异常写到SD卡中去
        write2SD(throwable)
        //将异常提交到服务器端
        upload2Server()
        throwable.printStackTrace()
        //如果系统自己提供的处理异常的机制就交给系统自己来处理，否则自己处理
        if (mUncaughtExceptionHandler != null) {
            mUncaughtExceptionHandler!!.uncaughtException(thread, throwable)
        } else {
            Process.killProcess(Process.myPid())
        }

        if (mContext is DtBaseApp) {
            (mContext as DtBaseApp).act?.showResultDialog("错误", "抱歉，发生了一点意外。")
        }
    }

    private fun write2SD(throwable: Throwable) {

        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            if (DEBUG) {
                Lwarn("sdcard unmounted, skip dump exception!")
            }
        }
        val dir = File(PATH)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val current = SystemClock.currentThreadTimeMillis()
        val currentTime = SimpleDateFormat("yyyy-MM-dd  HH:mm:ss",
                Locale.getDefault()).format(Date(current))
        val file = File(PATH + FILE_NAME + FILE_NAME_SUFFIX)
        try {
            val writer = PrintWriter(BufferedWriter(FileWriter(file)))
            writer.println(currentTime)
            writer.println("===================================================")
            writer.println(getPhoneInfo(writer))
            writer.println(throwable.message)
            writer.println("===================================================")
            throwable.printStackTrace(writer)
            writer.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 获取手机的硬件信息

     * @return
     */
    private fun getPhoneInfo(writer: PrintWriter): String? {
        val packageManager = mContext.packageManager
        try {
            val packageInfo = packageManager.getPackageInfo(mContext.packageName,
                    PackageManager.GET_ACTIVITIES)
            writer.println("App Vision:" + packageInfo.versionName)//当前APP的版本号
            writer.println("OS Vision:" + Build.VERSION.RELEASE + "__SDK INT:"
                    + Build.VERSION.SDK_INT)//当前操作系统版本
            writer.println("Vendor:" + Build.MANUFACTURER)//手机制造厂商
            writer.println("Moudle:" + Build.MODEL)//手机型号
            writer.println("CUP ABI:" + Build.CPU_ABI)//手CPU型号
            writer.println("===================================================")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 上传服务器端
     */
    private fun upload2Server() {
        //todo 上传到服务器
    }

    companion object {
        private val TAG = "CrashHandler"
        private val DEBUG = true
        //异常文件的路径
        private val PATH = Environment.getExternalStorageDirectory().path + "/CrashTest/log/"
        private val FILE_NAME = "crash"
        private val FILE_NAME_SUFFIX = ".trace"

        private val sInstance = CrashHandler()

        fun getsInstance(): CrashHandler {
            return sInstance
        }
    }
}
