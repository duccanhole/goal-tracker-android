package com.example.goaltracker.composable

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("title")
        val content = intent?.getStringExtra("content")
        val smallIcon = intent?.getIntExtra("smallIcon", 0)
        val notifyId = intent?.getStringExtra("notifyId")

        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo kênh thông báo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "custom_channel_id",
                "notify",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = smallIcon?.let {
            NotificationCompat.Builder(context, "custom_channel_id")
                .setSmallIcon(it)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
        }

        Log.d("Apps", "notifYID in Alarm :" + notifyId)
        notificationManager.notify(Math.abs(notifyId.hashCode()), builder?.build())
    }

}