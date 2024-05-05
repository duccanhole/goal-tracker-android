package com.example.goaltracker.navigation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonAddDisabled
import androidx.compose.material.icons.rounded.WifiOff
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.goaltracker.composable.statistic.RankBox
import com.example.goaltracker.composable.statistic.StatisticEllipse
import com.example.goaltracker.repositories.goal.GoalRepo
import com.example.goaltracker.repositories.user.LocalDataUser
import com.example.goaltracker.utils.ColorUtils
import com.example.goaltracker.utils.TextSizeUtils
import com.example.goaltracker.utils.isInternetAvailable

interface CoutCallback {
    fun onSuccess(Done:Int,Undone:Int)
    fun onError(errormessage: String)
}
interface Userlevel {
    fun onSuccess(level:Int)
    fun onError(errormessage: String)
}
fun getCountStatic(days:Number,callback: CoutCallback){
    GoalRepo.getInstance().countGoal(days) { res, err ->
        run {
            if (err!=null){
                callback.onError("Không lấy được !!")
            }else{
                Log.d("Apps","da vao duoc")
                Log.d("Apps", res?.result?.done?.toInt().toString())
                res?.result?.done?.let {
                    res?.result?.undone?.let { it1 ->
                        callback.onSuccess(
                            it.toInt(),
                            it1.toInt(),
                        )
                    }
                }
            }
        }
    }
}
fun  getUserlevel(callback:Userlevel){
    GoalRepo.getInstance().getUserLevel() { res, err ->
        run {
            if (err!=null){
                callback.onError("da xay ra loi")
            }else{
                Log.d("Apps","level : "+res?.result)
                res?.result?.toInt()?.let {
                    callback.onSuccess(
                        it
                    )
                }
        }
    }
}
}
@Composable()
fun StatisticPage() {
    val context = LocalContext.current
    val userToken = LocalDataUser(context).getToken()
    Log.d("Apps", userToken.toString())
    val isInternetAvailable = isInternetAvailable(context)
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember {
        mutableStateOf("3 ngày")
    }
    var done by remember {
        mutableStateOf(0)
    }
    var undone by remember {
        mutableStateOf(1)
    }
    var day by remember {
        mutableStateOf(3)
    }
    var Level by remember {
        mutableStateOf(1)
    }
    Surface(color = Color(ColorUtils.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (isInternetAvailable) {
                if (userToken == null) {
                    Icon(
                        imageVector = Icons.Rounded.PersonAddDisabled,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "Hãy đăng nhập để sử dụng chức năng này!",
                        fontSize = TextSizeUtils.MEDIUM
                    )
                } else {
                    LaunchedEffect(Unit) {
                        getUserlevel(object : Userlevel {
                            override fun onSuccess(level: Int) {
                                Level = level
                            }

                            override fun onError(errormessage: String) {
                                // Xử lý lỗi nếu cần
                            }
                        })

                        getCountStatic(day, object : CoutCallback {
                            override fun onSuccess(Done: Int, Undone: Int) {
                                done = Done
                                undone = Undone
                            }

                            override fun onError(errormessage: String) {
                                // Xử lý lỗi nếu cần
                            }
                        })
                    }
                    LaunchedEffect(selectedOption) {
                        getCountStatic(day, object : CoutCallback {
                            override fun onSuccess(Done: Int, Undone: Int) {
                                done = Done
                                Log.d("Apps", "done:" + done)
                                undone = Undone
                            }

                            override fun onError(errormessage: String) {
                            }
                        })
                    }

                    Text(
                        text = "Thống kê",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        fontSize = TextSizeUtils.LARGE,
                        fontWeight = FontWeight.Bold,

                        )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Khoảng thời gian ",
                            modifier = Modifier.weight(1f),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = TextSizeUtils.MEDIUM
                            )
                        )

                        Box(
                            modifier = Modifier
                                .clickable { expanded = !expanded }
                                .padding(8.dp)
                                .border(2.dp, color = Color.Black)
                                .width(80.dp)
                                .height(30.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                "${selectedOption} ▼",
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Center,
                                fontSize = TextSizeUtils.SMALL
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.widthIn(max = 150.dp)
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        selectedOption = "3 ngày"
                                        day = 3
                                        expanded = false
                                    }
                                ) {
                                    Text(text = "3 ngày")
                                }
                                DropdownMenuItem(onClick = {
                                    selectedOption = "1 tuần"
                                    day = 7
                                    expanded = false
                                }) {
                                    Text(text = "1 tuần")
                                }
                                DropdownMenuItem(onClick = {
                                    selectedOption = "1 tháng"
                                    day = 30
                                    expanded = false
                                }) {
                                    Text(text = "1 tháng")
                                }
                            }
                        }

                    }
                    StatisticEllipse((done * 100) / (undone + done))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Đã hoàn thành:$done")
                        Text(text = "Chưa hoàn thành:$undone")
                    }
                    Text(
                        text = " Thành tựu ",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = TextSizeUtils.MEDIUM
                        )
                    )


                    Log.d("Apps","level: "+Level.toString())
                    RankBox(Level)
                }

            } else {
                Icon(
                    imageVector = Icons.Rounded.WifiOff,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Không có mạng, vui lòng kiểm tra và thử lại.",
                    fontSize = TextSizeUtils.MEDIUM
                )

            }
        }
    }
}