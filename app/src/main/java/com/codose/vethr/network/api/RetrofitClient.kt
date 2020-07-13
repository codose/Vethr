package com.codose.vethr.network.api

import com.codose.vethr.utils.Constants.BASE_URL
import com.codose.vethr.utils.Constants.PLACES_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var mClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null

    private val client: OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  /// show all JSON in logCat
                mClient = httpBuilder.build()

            }
            return mClient!!
        }

    private val gsonConverter: GsonConverterFactory
        get() {
            if (mGsonConverter == null) {
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create()
                    )
            }
            return mGsonConverter!!
        }

    fun vethrService() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(gsonConverter)
        .client(client)
        .build()
        .create(VethrService::class.java)

    fun placeSearch() = Retrofit.Builder()
        .baseUrl(PLACES_URL)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(gsonConverter)
        .client(client)
        .build()
        .create(VethrService::class.java)
}