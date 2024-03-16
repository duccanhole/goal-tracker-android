package com.example.myapplication.repositories.user

import android.util.Log
import com.example.myapplication.utils.BaseUrl
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

object UserRepo {
    private var client: UserService? = null;
    private var instance: UserRepo? = null
    fun getInstance(): UserRepo {
        if (client == null) {
            val retrofit =
                Retrofit.Builder().baseUrl(BaseUrl.URL)
                    .addConverterFactory(GsonConverterFactory.create()).build()
            client = retrofit.create(UserService::class.java)
            synchronized(UserRepo::class.java) {
                if (instance == null) {
                    instance = UserRepo
                }
            }

        }

        return instance!!
    }

    fun login(username: String, password: String, callback: (LoginResponse?, Throwable?) -> Unit) {
        val payload = LoginRequest(username, password)
        val call = client?.login(payload)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful) {
                    response.body()?.let { callback(it, null) }
                }
                else {
                    callback(null, Exception("Error: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback(null, t)
            }

        })
    }
}