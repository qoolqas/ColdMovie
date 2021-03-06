package com.raveendra.coldmovie.connection

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private val BASE_URL: String = "https://api.themoviedb.org/3/"

private fun getClient(): Retrofit {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client : OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
    return Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

object getApi {
    val retrofitService: Service by lazy { getClient().create(Service::class.java) }
}