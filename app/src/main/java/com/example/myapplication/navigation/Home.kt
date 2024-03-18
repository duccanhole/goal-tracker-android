package com.example.myapplication.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.myapplication.composable.DashBoardPreview
import com.example.myapplication.composable.GoalList
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.goal.GoalRepo
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation

class GoalModel: ViewModel() {
    private var list = mutableStateListOf<Goal>()
    private val goalRepo = GoalRepo().getInstance()
    fun mergeData(local: Array<Goal> = emptyArray(), remote: Array<Goal> = emptyArray()) {
        val newArray = (local + remote).distinctBy {it._id}.toTypedArray()
        list.addAll(newArray)
    }

    fun remove() {

    }
}

@Composable()
fun HomePage(navController: NavController) {
    val context = LocalContext.current
    val userToken = LocalDataUser(context).getToken()
    val localData = LocalData(context).getAll()
    val remoteData = GoalRepo().getInstance(userToken)

    val goalModel = GoalModel()
//    Button(onClick = {
//        navController.navigate("sign-in")
//    }) {
//        Text(text = "navigate to sign-in page")
//    }
    remoteData.getGoalToday(){ res, err ->
        run {
            if (err != null) {
                goalModel.mergeData(localData)
            } else {
                goalModel.mergeData(localData, res!!.result)
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Navigation.CREAT_GOAL)
            }, backgroundColor = Color(ColorUtils.primary)) {
                Icon(Icons.Rounded.Add, contentDescription = null, tint = Color.White)
            }
        },
        backgroundColor = Color(ColorUtils.background)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            DashBoardPreview()
            GoalList(navController)
        }
    }
}