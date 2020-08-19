package com.vinstudio.vinsdks

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    val BASE_URL = "https://vin-crypto-market.herokuapp.com/"
    fun <T> buildService(service: Class<T>): T = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)
}
