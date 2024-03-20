package com.example.myapplication.composable

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title=intent?.getStringExtra("title")
        val content=intent?.getStringExtra("content")
        val smallIcon=intent?.getIntExtra("smallIcon",0)

        val notificationManager =context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = smallIcon?.let {
            NotificationCompat.Builder(context, "custom_channel_id")
                .setSmallIcon(it)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }
        notificationManager.notify(1, builder?.build())
    }
}