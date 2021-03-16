package com.raveendra.coldmovie.connection

import com.raveendra.coldmovie.model.discover.DiscoverMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("discover/movie")
    suspend fun getDiscover(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): DiscoverMovieResponse

}