package com.example.myapplication.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.navigation.NavController
import com.example.myapplication.composable.DashBoardPreview
import com.example.myapplication.composable.GoalList
import com.example.myapplication.repositories.goal.Goal
import com.example.myapplication.repositories.goal.GoalRepo
import com.example.myapplication.repositories.goal.LocalData
import com.example.myapplication.repositories.goal.UpdateAndCreateGoal
import com.example.myapplication.repositories.user.LocalDataUser
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.Navigation
import com.example.myapplication.utils.TextSizeUtils

class GoalModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val LIST_KEY = "goal_list"
    private val listGoal: MutableList<Goal> by lazy {
        savedStateHandle.get<MutableList<Goal>>(LIST_KEY) ?: mutableListOf()
    }
    var viewByDone by savedStateHandle.saveable{ mutableStateOf(false) }
    val list: List<Goal>
        get() = listGoal.filter { i -> i.isDone == viewByDone }
    var loadingRemove by savedStateHandle.saveable { mutableStateOf(false) }
    var itemUpdating by savedStateHandle.saveable{ mutableStateOf("") }
    private val goalRepo = GoalRepo.getInstance()
    private var localData: LocalData? = null
    fun setLocalData(localData: LocalData) {
        this.localData = localData
    }

    fun mergeData(local: Array<Goal> = emptyArray(), remote: Array<Goal> = emptyArray()) {
        listGoal.clear()
        val newArray = (local + remote).distinctBy { it._id }
        listGoal.addAll(newArray)
    }

    fun remove(goal: Goal, callback: () -> Unit) {
        loadingRemove = true
        goalRepo.removeGoal(goal._id) { response, throwable ->
            localData?.remove(goal)
            loadingRemove = false
            listGoal.remove(goal)
            callback()
        }
    }

    fun update(id: String, goal: UpdateAndCreateGoal, callback: () -> Unit) {
        itemUpdating = id
        goalRepo.updateGoal(id,goal) { response, throwable ->
            if(throwable != null)
                localData?.update(id, goal)
            else
                localData?.update(id, response!!.result)
            itemUpdating = ""
            callback()
        }
    }
}

val goalModel = GoalModel(SavedStateHandle())

@Composable()
fun HomePage(navController: NavController) {
    val context = LocalContext.current
    val userToken = LocalDataUser(context).getToken()
    val localData = LocalData(context)
    val remoteData = GoalRepo.getInstance(userToken)
    var loading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        loading = true
        goalModel.setLocalData(localData)
        remoteData.getGoalToday() { res, err ->
            val listLocal = localData.getAll()
            if (err != null) {
                goalModel.mergeData(listLocal)
            } else {
                goalModel.mergeData(listLocal, res!!.result)
            }
            loading = false
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
            DashBoardPreview(list = goalModel.list)
            if (loading)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    CircularProgressIndicator(
                        color = Color(ColorUtils.accent),
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .padding(20.dp)
                    )
                    Text(text = "Đang lấy dữ liệu ...", fontSize = TextSizeUtils.MEDIUM)
                }
            else GoalList(navController, goalModel)
        }
    }

}
