package com.envios.kitabisa.data.remote

import com.envios.kitabisa.data.remote.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    fun getPopularMovieAsync(@Query("api_key") apiKey:String, @Query("language") language:String, @Query("page") page:String): Deferred<Response<PopularResponse>>

    @GET("movie/top_rated")
    fun getTopRatedMovieAsync(@Query("api_key") apiKey:String, @Query("language") language:String, @Query("page") page:String): Deferred<Response<TopRatedResponse>>

    @GET("movie/now_playing")
    fun getNowPlayingMovieAsync(@Query("api_key") apiKey:String, @Query("language") language:String, @Query("page") page:String): Deferred<Response<NowPlayingResponse>>


    @GET("movie/{movie_id}/reviews")
    fun getReviewAsync(@Path("movie_id") movieId: String, @Query("api_key") apiKey:String): Deferred<Response<ReviewResponse>>


    @GET("movie/{movie_id}")
    fun getMovieDetailAsync(@Path("movie_id") movieId: String, @Query("api_key") apiKey:String): Deferred<Response<MovieDetail>>


    @GET("genre/movie/list")
    fun getMovieGenreAsync(@Query("api_key") apiKey:String, @Query("language") language:String): Deferred<Response<GenreResponse>>

    @GET("discover/movie")
    fun getMoviesByGenreAsync(@Query("api_key") apiKey:String, @Query("with_genres") with_genres:String, @Query("page") page:String): Deferred<Response<PopularResponse>>

}