package com.example.goaltracker.repositories.goal

import android.util.Log
import com.example.goaltracker.utils.BaseUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object GoalRepo {
    private var client: GoalService? = null;
    private var instance: GoalRepo? = null;
    private var token: String? = null
    fun getInstance(token: String? = null): GoalRepo {
        if (client == null || this.token == null) {
            val httpClient = OkHttpClient.Builder()
            if (!token.isNullOrEmpty()) {
                this.token = token
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
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
            client = retrofit.create(GoalService::class.java)
            synchronized(GoalRepo::class.java) {
                if (instance == null) {
                    instance = GoalRepo
                }
            }
        }

        return instance!!
    }

    fun getGoalToday(callback: (Response<Array<Goal>>?, Throwable?) -> Unit) {
        val call = client?.getGoalToday()
        call?.enqueue(object : Callback<Response<Array<Goal>>> {
            override fun onResponse(
                call: Call<Response<Array<Goal>>>,
                response: retrofit2.Response<Response<Array<Goal>>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<Array<Goal>>>, t: Throwable) {
                t.message?.let { Log.e("App", it) }
                callback(null, t)
            }
        })
    }

    fun getGoalDetail(id: String, callback: (Response<Goal>?, Throwable?) -> Unit) {
        val call = client?.getGoalDetail(id)
        call?.enqueue(object: Callback<Response<Goal>> {
            override fun onResponse(
                call: Call<Response<Goal>>,
                response: retrofit2.Response<Response<Goal>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<Goal>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun createGoal(goal: UpdateAndCreateGoal, callback: (Response<Goal>?, Throwable?) -> Unit) {
        val call = client?.createGoal(goal)
        call?.enqueue(object : Callback<Response<Goal>> {
            override fun onResponse(
                call: Call<Response<Goal>>,
                response: retrofit2.Response<Response<Goal>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<Goal>>, t: Throwable) {
                t.message?.let { Log.e("App", it) }
                callback(null, t)
            }
        })
    }

    fun updateGoal(
        id: String,
        goal: UpdateAndCreateGoal,
        callback: (Response<Goal>?, Throwable?) -> Unit
    ) {
        val call = client?.updateGoal(id, goal)
        call?.enqueue(object : Callback<Response<Goal>> {
            override fun onResponse(
                call: Call<Response<Goal>>,
                response: retrofit2.Response<Response<Goal>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<Goal>>, t: Throwable) {
                t.message?.let { Log.e("App", it) }
                callback(null, t)
            }
        })
    }

    fun removeGoal(id: String, callback: (Response<String>?, Throwable?) -> Unit) {
        val call = client?.removeGoal(id)
        call?.enqueue(object : Callback<Response<String>> {
            override fun onResponse(
                call: Call<Response<String>>,
                response: retrofit2.Response<Response<String>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun countGoal(days: Number = 0, callback: (Response<CountResult>?, Throwable?) -> Unit) {
        val call = client?.countGoal(days)
        call?.enqueue(object : Callback<Response<CountResult>> {
            override fun onResponse(
                call: Call<Response<CountResult>>,
                response: retrofit2.Response<Response<CountResult>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<CountResult>>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun getUserLevel(callback: (Response<Number>?, Throwable?) -> Unit) {
        val call = client?.getUserLevel()
        call?.enqueue(object : Callback<Response<Number>> {
            override fun onResponse(
                call: Call<Response<Number>>,
                response: retrofit2.Response<Response<Number>>
            ) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Exception("An error has occured, code: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Response<Number>>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}
