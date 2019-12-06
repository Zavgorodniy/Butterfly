package com.butterfly.test.network.api

import com.butterfly.test.network.bean.butterfly.ResponseButterfly
import io.reactivex.Single
import retrofit2.http.GET

interface ButterflyApi {

    @GET("v1/volumes?q=butterfly ")
    fun getAll(): Single<ResponseButterfly>
}