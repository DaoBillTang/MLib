package com.daotangbill.exlib.commons.permissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.daotangbill.exlib.commons.logger.Linfo
import java.util.*

/**
 * project com.daotangbill.rxextlib.lib.commons.permissions
 * Created by Bill on 2017/11/1.
 * emal: tangbakzi@daotangbill.uu.me
 *
 * @author Bill
 * @version 1.0
 * @description
 */
/**
 *  使用的时候 实现 此callback ,然后调用相关的方法即可来获取 permission
 */
interface PermissionCallbacks : ActivityCompat.OnRequestPermissionsResultCallback {
    fun onPermissionsGranted(requestCode: Int, perms: List<String>)

    fun onPermissionsDenied(requestCode: Int, perms: List<String>)
}

/**
 *@param perms 需要检测的 权限
 * @return
 * true :拥有权限
 * false :缺失权限
 */
fun Context?.hasPermissions(perms: Array<String>): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this?.Linfo { "hasPermissions: API version < M, returning true by default" }
        return true
    }

    if (this == null) {
        throw IllegalArgumentException("Can't check permissions for null context")
    }

    return perms.none {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    }
}

/**
 *@param perms 需要检测的 权限
 * @return
 * true :拥有权限
 * false :缺失权限
 */
fun Context?.hasPermissions(perms: String): Boolean {
    // Always return true for SDK < M, let the system deal with the permissions
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this?.Linfo { "hasPermissions: API version < M, returning true by default" }
        return true
    }

    if (this == null) {
        throw IllegalArgumentException("Can't check permissions for null context")
    }

    return ContextCompat.checkSelfPermission(this, perms) != PackageManager.PERMISSION_GRANTED
}

/**
 * activity 中获取权限的操作
 *@param perms 需要的权限，以下为所有的危险权限：
 * group:android.permission-group.CONTACTS
permission:android.permission.WRITE_CONTACTS
permission:android.permission.GET_ACCOUNTS
permission:android.permission.READ_CONTACTS

group:android.permission-group.PHONE
permission:android.permission.READ_CALL_LOG
permission:android.permission.READ_PHONE_STATE
permission:android.permission.CALL_PHONE
permission:android.permission.WRITE_CALL_LOG
permission:android.permission.USE_SIP
permission:android.permission.PROCESS_OUTGOING_CALLS
permission:com.android.voicemail.permission.ADD_VOICEMAIL

group:android.permission-group.CALENDAR
permission:android.permission.READ_CALENDAR
permission:android.permission.WRITE_CALENDAR

group:android.permission-group.CAMERA
permission:android.permission.CAMERA

group:android.permission-group.SENSORS
permission:android.permission.BODY_SENSORS

group:android.permission-group.LOCATION
permission:android.permission.ACCESS_FINE_LOCATION
permission:android.permission.ACCESS_COARSE_LOCATION

group:android.permission-group.STORAGE
permission:android.permission.READ_EXTERNAL_STORAGE
permission:android.permission.WRITE_EXTERNAL_STORAGE

group:android.permission-group.MICROPHONE
permission:android.permission.RECORD_AUDIO

group:android.permission-group.SMS
permission:android.permission.READ_SMS
permission:android.permission.RECEIVE_WAP_PUSH
permission:android.permission.RECEIVE_MMS
permission:android.permission.RECEIVE_SMS
permission:android.permission.SEND_SMS
permission:android.permission.READ_CELL_BROADCASTS
 * @param requestCode 请求判定值
 *
 *
 *
 */
fun Activity?.toRequestPermissions(perms: Array<String>,
                                   requestCode: Int): Boolean {
    if (this == null) {
        throw IllegalArgumentException("Can't request permissions for null context")
    }
    // Check for permissions before dispatching the request
    if (hasPermissions(perms)) {
        if (this is PermissionCallbacks) {
            onPermissionsGranted(requestCode, perms.toList())
        }
        return true
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.requestPermissions(perms, requestCode)
    }
    return false
}

fun Fragment?.toRequestPermissions(perms: Array<String>,
                                   requestCode: Int) {
    if (this == null) {
        throw IllegalArgumentException("Can't request permissions for null context")
    }
    val act = this.activity ?: throw IllegalArgumentException("Can't request permissions for null Activity")
    act.toRequestPermissions(perms, requestCode)
}
/*
 * 对于 第一次拒绝的 选择给出的更多的解释
 * ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,permission )
 */

/**
 * should use in activity/Fragment/v4.Fragment onRequestPermissionsResult
 */
fun Any.PermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    val granted = ArrayList<String>()
    val denied = ArrayList<String>()
    for (i in permissions.indices) {
        val perm = permissions[i]
        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
            granted.add(perm)
        } else {
            denied.add(perm)
        }
    }

    // iterate through all receivers
    // Report granted permissions, if any.
    if (granted.isNotEmpty()) {
        if (this is PermissionCallbacks) {
            this.onPermissionsGranted(requestCode, granted)
        }
    }

    // Report denied permissions, if any.
    if (denied.isNotEmpty()) {
        if (this is PermissionCallbacks) {
            this.onPermissionsDenied(requestCode, denied)
        }
    }
}