package com.daotangbill.exlib.commons.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.os.Environment
import com.daotangbill.exlib.commons.logger.Lerror
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * project com.ecentm.customer.utils
 * Created by Bill on 2017/11/10.
 * emal: tangbakzi@daotangbill.uu.me
 * @author Bill
 * @version 1.0
 * @description bitmapUtils
 */
object BitmapUtils {
    /**
     * sd卡的根目录
     */
    private val SD_ROOT_PATH = Environment.getExternalStorageDirectory().path

    /**
     * 手机的缓存根目录
     */
    private var mDataRootPath: String? = null

    /**
     * 保存Image的目录名
     */
    private val FOLDER_NAME = "/ecentm"

    fun getStorageDirectory(context: Context): String {
        mDataRootPath = context.cacheDir.path
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
            SD_ROOT_PATH + FOLDER_NAME
        else
            mDataRootPath + FOLDER_NAME
    }

    /**
     * getExternalCacheDir
     * getExternalFilesDir
     */
    fun getFileRoot(context: Context): String {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val external = context.getExternalFilesDir(null)
            if (external != null) {
                return external.absolutePath
            }
        }
        return context.filesDir.absolutePath
    }

    fun saveBitmapFile(context: Context, bitmap: Bitmap, name: String): String? {
        val patht = (getFileRoot(context) + "/cache/")
        val path = patht + name + ".jpg"
        val fileDirs = File(patht)
        val file = File(path)//将要保存图片的路径
        return try {
            if (!fileDirs.exists()) {
                fileDirs.mkdirs()
            }
            val bos = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
            path
        } catch (e: IOException) {
            Lerror { e.toString() }
            e.printStackTrace()
            null
        }
    }

    fun saveDrawableFile(context: Context, drawable: Drawable?, name: String): String? {
        val bitmap = drawableToBitmap(drawable)
        if (bitmap == null) {
            Lerror { "没有bitmap" }
            return null
        } else {
            Lerror { "有bitmap" }
        }
        return saveBitmapFile(context, bitmap, name)
    }

    fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            Lerror { "没有图片" }
            return null
        }
        Lerror { "有图片" }
        val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * @name name 需要用 .jpg 结尾
     */
    fun getBitmapUrl(context: Context, name: String): String? {
        return (getFileRoot(context) + "/cache/" + name + ".jpg")
    }
}