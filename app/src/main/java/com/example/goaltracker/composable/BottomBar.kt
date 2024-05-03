
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PieChart
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign

import androidx.navigation.NavController
import com.example.goaltracker.utils.ColorUtils.primary

@Composable
fun BottomBar(navController: NavController){
   BottomAppBar(
       containerColor = Color(primary),
       contentColor = Color.White
   ) {
       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceEvenly,

           ) {
           IconWithText(Icons.Rounded.Home, "Trang chủ"){
               navController.navigate("home")
           }
           IconWithText(Icons.Rounded.PieChart, "Thống kê"){
               navController.navigate("statistic")
           }
           IconWithText(Icons.Rounded.Settings, "Cài đặt"){
               navController.navigate("setting")
           }
       }
   }
}
@Composable
fun IconWithText(icon: ImageVector, text: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(icon, contentDescription = null,modifier = Modifier.clickable { onClick() })
        Text(text,
            textAlign = TextAlign.Center)
    }
}
