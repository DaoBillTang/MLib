package com.daotangbill.exlib.base

interface DtBaseView {
    fun showProgressDialog(msg: String?)
    fun showProgressDialog()
    fun proDialogDismiss()
    fun showMsg(msg: String)
    fun showError(msg: String)
}