package com.vinstudio.vinsdks

import com.vinstudio.vinsdk.ResultPromo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/apps")
    fun getAllApp(@Query("platform") platform: String): Call<Result>

    @GET("api/apps/promo")
    fun getPromo(@Query("platform") platform: String): Call<ResultPromo>
}
