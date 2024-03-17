package com.example.myapplication.repositories.goal

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
}