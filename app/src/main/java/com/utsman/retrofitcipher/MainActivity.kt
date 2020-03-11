package com.utsman.retrofitcipher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utsman.retrofitcipher.network.NetworkInstance
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkInstance.create().getHello().enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                t.printStackTrace()
                logi(t.message)
                text_view_hello.text = t.message
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                val textHello = response.body()?.body
                text_view_hello.text = textHello
            }
        })

        btn_post.setOnClickListener {
            NetworkInstance.create().postHello("hello njir".encrypt())
                    .enqueue(object : Callback<Response> {
                        override fun onFailure(call: Call<Response>, t: Throwable) {
                            t.printStackTrace()
                            text_view_hello.text = t.message
                        }

                        override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                            val textResponse = response.body()?.body
                            text_view_hello.text = textResponse
                        }

                    })
        }
    }
}
