
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.repositories.goal.GoalRepo
import com.example.myapplication.utils.ColorUtils
import com.example.myapplication.utils.TextSizeUtils
interface CoutCallback {
    fun onSuccess(Done:Int,Undone:Int)
    fun onError(errormessage: String)
}
fun getCountStatic(callback: CoutCallback){
    GoalRepo.getInstance().countStatic { res, err ->
        run {
            if (err!=null){
                callback.onError("Không lấy được !!")
            }else{
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
@Composable
@Preview
fun StatisticPage() {

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("3 ngày") }
    var done by remember {
        mutableStateOf(0)
    }
    var undone by remember {
        mutableStateOf(1)
    }
    getCountStatic(object :CoutCallback{
        override fun onSuccess(Done: Int, Undone: Int) {
            done=Done
            undone=Undone
        }
        override fun onError(errormessage: String) {
            TODO("Not yet implemented")
        }

    })
    Surface(
        color = Color(ColorUtils.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Thống kê",
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally)
                ,
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
                Text(text = "Khoảng thời gian ", modifier = Modifier.weight(1f), style = TextStyle(fontWeight = FontWeight.Bold, fontSize = TextSizeUtils.MEDIUM))

                Box(
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .padding(8.dp)
                        .border(2.dp, color = Color.Black)
                        .width(80.dp)
                        .height(30.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text("${selectedOption} ▼", modifier = Modifier.fillMaxSize(),textAlign = TextAlign.Center, fontSize = TextSizeUtils.SMALL)
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.widthIn(max = 150.dp)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = "3 ngày"
                                expanded = false
                            }
                        ) {
                            Text(text = "3 ngày")
                        }
                        DropdownMenuItem(onClick = {
                            selectedOption = "1 tuần"
                            expanded = false
                        }) {
                            Text(text = "1 tuần")
                        }
                        DropdownMenuItem(onClick = {
                            selectedOption = "1 tháng"
                            expanded = false
                        }) {
                            Text(text = "1 tháng")
                        }
                    }
                }

            }
            StaticEllipse((done * 100) / (undone+done))
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
            Text(text = " Thành tựu ", style = TextStyle(fontWeight = FontWeight.Bold, fontSize = TextSizeUtils.MEDIUM))
            RankBox(goalcomplete = 100)
        }
    }
}

@Composable
fun StaticEllipse(percent: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val centerX = canvasWidth / 2f
            val centerY = canvasHeight / 2f

            val radius = if (canvasWidth < canvasHeight) canvasWidth / 3f else canvasHeight / 3f

            val angle = 360f * percent / 100f



            // Draw the complete ellipse with black color
            drawArc(
                color = Color.White,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw the filled portion of the ellipse with blue color
            drawArc(
                color = Color(ColorUtils.primary),
                startAngle = -90f,
                sweepAngle = angle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "${percent.toInt()}%",
                modifier= Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = TextSizeUtils.LARGE
            )
            Text(
                text = "Mục tiêu đã hoàn thành",
                modifier= Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black,
            )
        }
    }
}
@SuppressLint("DiscouragedApi")
@Composable
fun RankBox(goalcomplete:Int){
    val context = LocalContext.current
    val rank by remember {
        mutableStateOf(
            when {
                goalcomplete > 99 -> 4
                goalcomplete > 49 -> 3
                goalcomplete > 19 -> 2
                goalcomplete > 9 -> 1
                else -> 0
            }
        )
    }
    val imageFileName = "rank$rank"
    val imageResourceId = context.resources.getIdentifier(imageFileName, "drawable", context.packageName)
    val painter = painterResource(id = imageResourceId)
    Box(
        modifier = Modifier
            .padding(20.dp)
            .background(Color(ColorUtils.accent), shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = "Rank__image"
            )
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Thành tựu $rank",
                    modifier= Modifier.align(Alignment.CenterHorizontally),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = TextSizeUtils.LARGE)
                Text(text = "Đã hoàn thành $goalcomplete số mục tiêu đề ra",
                    modifier= Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp, 0.dp),
                    color = Color.Black,
                    fontSize = TextSizeUtils.SMALL
                )
            }

        }
    }
}

