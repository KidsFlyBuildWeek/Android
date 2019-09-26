package com.ali.kidsfly

import com.ali.kidsfly.model.UserProfile
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface TripApi {

    @POST("parents/new")
    fun postUserProfile(@Body user: UserProfile) : Call<UserProfile>

    @GET("parents/{parentid}")
    fun getUserProfileInformation(@Query("parentid")parentid: String): Call<UserProfile>

    companion object{

        private const val BASE_URL = "https://kidsflybackend.herokuapp.com/"

        fun getTripApiCall(): TripApi{
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

            return retrofit.create(TripApi::class.java)
        }
    }
}