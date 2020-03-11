package com.utsman.retrofitcipher.network

import com.utsman.retrofitcipher.decrypt
import com.utsman.retrofitcipher.logi
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class DecryptInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val newResponse = response.newBuilder()
        val responseString = response.body?.string()
        val decryptResponse = responseString?.decrypt()

        logi(" -- \n rawResponse -> $responseString \n decryptResponses -> $decryptResponse")

        var contentType = response.header("Content-Type")
        if (contentType?.isEmpty() == true) contentType = "application/json"

        newResponse.body(decryptResponse?.toResponseBody(contentType?.toMediaTypeOrNull()))
        return newResponse.build()
    }
}