package com.example.myapplication.repositories.goal

import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.Date

data class Goal(
    val _id: String,
    val name: String,
    val user: String?,
    var isDone: Boolean = false,
    val hasNotfication: Boolean = false,
    val notifyAt: String,
    val createdAt: String,
)

class GoalService {
    @GET("/goal/today")
    fun getGoalToday(){}

    @GET("/goal/{id}")
    fun getGoalDetail(@Path("id") id: String){}

    @DELETE("/goal/{id}")
    fun removeGoal(@Path("id") id: String){}

    @POST("/goal/create")
    fun createGoal(){}

    @PUT("/goal/update")
    fun updateGoal(){}
}