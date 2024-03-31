import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.myapplication.composable.AlarmReceiver
import java.util.Objects

@SuppressLint("ScheduleExactAlarm")
fun setupNotification(context:Context, notifyId:String, title:String, content:String, smallIcon: Int, notificationTime: Long?){
    if(notificationTime == null) return
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("title", title)
        putExtra("content", content)
        putExtra("smallIcon", smallIcon)
        putExtra("notifyId",notifyId)
    }
    val flags = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        else -> FLAG_UPDATE_CURRENT
    }

    val pendingIntent = PendingIntent.getBroadcast(context,Math.abs(notifyId.hashCode()), intent,flags)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)
    } else {
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)
    }
}

fun cancelNotification(context: Context, notifyId: String) {
    val flags = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        else -> FLAG_UPDATE_CURRENT
    }
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    val intent = Intent(context, AlarmReceiver::class.java)


    val pendingIntent = PendingIntent.getBroadcast(context, Math.abs(notifyId.hashCode()), intent,flags )


    pendingIntent?.let {
        alarmManager.cancel(it)
        it.cancel()
    }
}