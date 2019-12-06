package com.butterfly.test.network.api

import com.butterfly.test.network.bean.cat.ResponseCat
import io.reactivex.Single
import retrofit2.http.GET

interface CatApi {

    @GET("facts")
    fun getAll(): Single<ResponseCat>
}