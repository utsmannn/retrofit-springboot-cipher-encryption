package com.utsman.retrofitcipher.network

import com.utsman.retrofitcipher.Response
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NetworkInstance {

    @GET("/api/hello")
    fun getHello(): Call<Response>

    @POST("/api/hello_post")
    fun postHello(@Query("hello") hello: String): Call<Response>

    companion object {
        private fun client(): OkHttpClient {
            return OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .addInterceptor(DecryptInterceptor())
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        }

        fun create() : NetworkInstance {
            val builder = Retrofit.Builder()
                .baseUrl("http:/192.168.43.112:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client())
                .build()

            return builder.create(NetworkInstance::class.java)
        }
    }
}