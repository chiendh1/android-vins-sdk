package com.vinstudio.vinsdks

import com.vinstudio.vinsdk.ClickApp
import com.vinstudio.vinsdk.ResultPromo
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/apps")
    fun getAllApp(
        @Query("platform") platform: String,
        @Query("excludeId") excludeId: String
    ): Call<Result>

    @GET("api/apps/promo")
    fun getPromo(
        @Query("platform") platform: String,
        @Query("excludeId") excludeId: String
    ): Call<ResultPromo>

    @POST("api/apps/click")
    @FormUrlEncoded
    fun postClickApp(
        @Field("targetAppId") targetAppId: String,
        @Field("appClientId") appClientId: String
    ): Call<ClickApp>
}
