package com.ali.kidsfly.api

import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.model.UserProfile
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface UserApi {

    @POST("parents/new")
    fun postUserProfile(@Body user: UserProfile) : Call<Unit>

    @GET("parents/{parentid}")
    fun getUserProfileInformation(@Path("parentid") parentid: Int): Call<DownloadedUserProfile>

    @DELETE
    fun deleteUserProfile(user: UserProfile) //permanently deletes user profile

    @PUT("parents/{parentid}")
    fun updateUserProfile(@Path("parentid") parentid: Int)

    companion object{

        private const val BASE_URL = "https://kidsflybackend.herokuapp.com/"

        fun getUserApiCall(): UserApi {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            logger.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .retryOnConnectionFailure(false)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(UserApi::class.java)
        }
    }
}