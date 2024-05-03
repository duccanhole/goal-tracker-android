package com.example.myapplication.repositories.goal

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class Goal(
    val _id: String,
    val name: String,
    val user: String?,
    var isDone: Boolean = false,
    val hasNotification: Boolean = false,
    val notifyAt: String,
    val createdAt: String,
)

data class UpdateAndCreateGoal(
    val name: String? = null,
    val user: String? = null,
    val isDone: Boolean = false,
    val hasNotification: Boolean = false,
    val notifyAt: String? = null
)

data class Response<T>(
    val result: T
)
data class CountStatic(
    val done:Int,
    val undone:Int
)
interface GoalService {
    @GET("/goal/today")
    fun getGoalToday(): Call<Response<Array<Goal>>>

    @GET("/goal/{id}")
    fun getGoalDetail(@Path("id") id: String): Call<Response<Goal>>

    @DELETE("/goal/{id}")
    fun removeGoal(@Path("id") id: String): Call<Response<String>>

    @POST("/goal/create")
    fun createGoal(@Body body: UpdateAndCreateGoal): Call<Response<Goal>>

    @PUT("/goal/{id}")
    fun updateGoal(@Path("id") id: String,@Body body: UpdateAndCreateGoal): Call<Response<Goal>>

    @GET("/goal/count")
    fun getCountStatic():Call<Response<CountStatic>>
}