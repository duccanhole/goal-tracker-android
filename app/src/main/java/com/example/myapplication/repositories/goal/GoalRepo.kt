package com.example.myapplication.repositories.goal

import com.example.myapplication.repositories.user.UserRepo
import com.example.myapplication.utils.BaseUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GoalRepo {
    private var client: GoalService? = null;
    private var instance: GoalRepo? = null
    fun getInstance(token: String? = null): GoalRepo {
        if (client == null) {
            val httpClient = OkHttpClient.Builder()
            if (!token.isNullOrEmpty()) {
                // Add an interceptor to add headers to every request
                httpClient.addInterceptor { chain ->
                    val original: Request = chain.request()

                    // Customize the request
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .header("token", token) // Example header
                        .method(original.method(), original.body())
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                }
            }
            val retrofit =
                Retrofit.Builder().baseUrl(BaseUrl.URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(httpClient.build())
                    .build()
            client = retrofit.create(GoalService::class.java)
            synchronized(UserRepo::class.java) {
                if (instance == null) {
                    instance = GoalRepo()
                }
            }

        }

        return instance!!
    }

    fun getGoalToday(callback: (Response<Array<Goal>>?, Throwable?) -> Unit) {
        val call = client?.getGoalToday()
        call?.enqueue(object: Callback<Response<Array<Goal>>> {
            override fun onResponse(
                call: Call<Response<Array<Goal>>>,
                response: retrofit2.Response<Response<Array<Goal>>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                }
                else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<Array<Goal>>>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}