package com.dtb.utils.sample

import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by BILL on 2016/12/27.
 * email:tangbaozi@daotangbill.uu.me
 *
 * @author BILL
 * @version 1.0
 */
class ApiService private constructor() {

    private val mRxInterFace: Api = NetClient.newRxRetrofit().create(Api::class.java)

    companion object {
        private var apiService: ApiService? = null
        val instance: ApiService
            get() {
                synchronized(ApiService::class.java) {
                    if (apiService == null) {
                        apiService = ApiService()
                    }
                }
                return apiService!!
            }
    }

    fun getObserver(): Observable<Response<String>> {
        return mRxInterFace.getObserver()
    }
}