package ru.mail.technotrack.recyclerview

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat

internal fun createSimpleNotification(context: Context) {
    val notificationIntent = Intent(context, MainActivity::class.java)
    notificationIntent.action = Intent.ACTION_MAIN
    notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER)

    notificationIntent.putExtra("notification", true)
    val contentIntent = PendingIntent.getActivity(
        context,
        0, notificationIntent,
        PendingIntent.FLAG_CANCEL_CURRENT
    )

    val msg = "Это тестовая нотификация"


    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        NotificationCompat.Builder(context, getChannelId(context,"my_channel_id",
            "My default Channel", "my_group_id", "My default Group"))
    else
        NotificationCompat.Builder(context, "")

    builder.setContentIntent(contentIntent)
        .setSmallIcon(R.drawable.technotrack_24)
        .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.technotrack_128))
        .setWhen(System.currentTimeMillis())
        .setShowWhen(true)
        .setAutoCancel(true)
        .setContentTitle("Напоминание")
        .setContentText(msg)

    builder.setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND)

    val nc = builder.build()

    val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    nm.notify(10, nc)
}

@TargetApi(Build.VERSION_CODES.O)
internal fun getChannelId(context : Context, channelId: String, name: String, groupId: String, groupName: String): String {
    val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val channels = nm.notificationChannels
    for (channel in channels) {
        if (channel.id == channelId) {
            return channel.id
        }
    }

    val group = getNotificationChannelGroupId(context, groupId, groupName)
    val importance = NotificationManager.IMPORTANCE_LOW
    val notificationChannel = NotificationChannel(channelId, name, importance)
    notificationChannel.enableLights(true)
    notificationChannel.lightColor = Color.RED
    notificationChannel.enableVibration(true)

    notificationChannel.group = group // set custom group

    notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
    nm.createNotificationChannel(notificationChannel)

    return channelId
}

@TargetApi(Build.VERSION_CODES.O)
internal fun getNotificationChannelGroupId(context : Context, groupId: String, name: String): String {
    val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val groups = nm.notificationChannelGroups
    for (group in groups) {
        if (group.id == groupId) {
            return group.id
        }
    }
    nm.createNotificationChannelGroup(NotificationChannelGroup(groupId, name))
    return groupId
}
