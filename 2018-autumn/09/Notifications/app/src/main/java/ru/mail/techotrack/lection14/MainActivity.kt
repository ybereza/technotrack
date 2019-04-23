package ru.mail.techotrack.lection14

import android.annotation.TargetApi
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.widget.Button
import android.widget.RemoteViews
import kotlinx.coroutines.*

import java.util.ArrayList

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 : Button = findViewById(R.id.send_notification_1)
        val button2 : Button = findViewById(R.id.send_notification_2)
        val button3 : Button = findViewById(R.id.send_notification_3)
        val button4 : Button = findViewById(R.id.send_notification_4)
        val button5 : Button = findViewById(R.id.send_notification_5)

        button1.setOnClickListener { createSimpleNotification() }

        button2.setOnClickListener { createGroupNotification() }
        button3.setOnClickListener { createActionNotification() }
        button4.setOnClickListener { createProgressNotification() }
        button5.setOnClickListener { crateCustomNotification() }
    }

    private fun createSimpleNotification() {
        val contentIntent = getHelperActivity(this, 1)

        val msg = "$count - Пора уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло"
        count++


        val builder = NotificationCompat.Builder(this, getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"))
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.technotrack_24)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.technotrack_128))
                .setTicker("Last china warning")
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setAutoCancel(true)
                .setContentTitle("Напоминание")
                //.setStyle(new Notification.BigTextStyle().bigText(msg));
                .setContentText(msg)

        var defaults = 0
        defaults = defaults or Notification.DEFAULT_VIBRATE
        defaults = defaults or Notification.DEFAULT_SOUND

        builder.setDefaults(defaults)

        val nc = builder.build()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(10, nc)
    }

    private fun createGroupNotification() {
        val contentIntent = getHelperActivity(this, 2)
        val msg = "$count - Пора уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло"
        count++
        messages.add(msg)

        val builder = NotificationCompat.Builder(this, getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"))
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.technotrack_24)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.technotrack_128))
                .setTicker("Last china warning")
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setAutoCancel(true)
                .setUsesChronometer(true)
                .setContentTitle("Напоминание")
        //.setStyle(new Notification.BigTextStyle().bigText(msg));
        //.setContentText(msg);

        val inbox = NotificationCompat.InboxStyle(builder)
        //inbox.addLine(msg);

        var count = 0
        var more = 0
        val groupId = 12
        for (m in messages) {
            if (count <= 5) {
                inbox.addLine(m)
            } else {
                ++more
            }
            ++count
        }

        if (more > 0)
            inbox.setSummaryText("+$more more")
        val nc = inbox.build()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(13, nc)
    }


    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    internal fun createActionNotification() {
        val contentIntent = getHelperActivity(this, 3)

        val notificationIntent = Intent(this, HelperActivity::class.java)
        notificationIntent.putExtra("todo", true)
        val piTodo = PendingIntent.getActivity(this,
                1, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        val msg = "$count - Надо что-то сделать"
        count++

        val builder = NotificationCompat.Builder(this, getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"))
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.technotrack_24)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.technotrack_128))
                .setTicker("Last china warning")
                .setWhen(System.currentTimeMillis())
                .addAction(NotificationCompat.Action.Builder(R.drawable.social_add_person, "Previous", piTodo).build()) // #0)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setContentTitle("To do")
                //.setStyle(new Notification.BigTextStyle().bigText(msg));
                .setContentText(msg)

        var defaults = 0
        defaults = defaults or Notification.DEFAULT_VIBRATE
        defaults = defaults or Notification.DEFAULT_SOUND
        builder.setDefaults(defaults)

        val nc = builder.build()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(10, nc)
    }

    private fun getChannelId(channelId: String, name: String, groupId: String, groupName: String) : String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getChannelIdInternal(channelId, name, groupId, groupName)
        }
        return ""
    }

    private fun getNotificationChannelGroupId(groupId: String, name: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return getNotificationChannelGroupIdInternal(groupId, name)
        }
        return ""
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun getChannelIdInternal(channelId: String, name: String, groupId: String, groupName: String): String {

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channels = nm.notificationChannels
        for (channel in channels) {
            if (channel.id == channelId) {
                return channel.id
            }
        }

        val group = getNotificationChannelGroupId(groupId, groupName)
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
    private fun getNotificationChannelGroupIdInternal(groupId: String, name: String): String {
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val groups = nm.notificationChannelGroups
        for (group in groups) {
            if (group.id == groupId) {
                return group.id
            }
        }
        nm.createNotificationChannelGroup(NotificationChannelGroup(groupId, name))
        return groupId
    }

    private fun createProgressNotification() {
        val msg = "$count - Пока уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло"
        count++

        val contentIntent = getHelperActivity(this, 10)
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"))
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.technotrack_24)
                .setContentTitle("To do")
                //.setStyle(new Notification.BigTextStyle().bigText(msg));
                .setContentText(msg)

        GlobalScope.launch {
            var incr = 0
            while (incr <= 100) {
                builder.setProgress(100, incr, false)
                nm.notify(0, builder.build())
                Thread.sleep((5 * 1000).toLong())

                incr += 5
            }
            // When the loop is finished, updates the notification
            builder.setContentText("Download complete")
                    .setProgress(0, 0, false)
            nm.notify(123, builder.build())
        }
    }

    private fun crateCustomNotification() {
        val contentIntent = getHelperActivity(this, 1)

        val title = "Это custom нотификация!"
        val msg = "$count - Пора уже таки покормить рыбок, они почти сдохли, это специально длинный текст такой чтобы не влезло"
        count++

        val rv = RemoteViews(getPackageName(), R.layout.custom_notification)
        rv.setTextViewText(R.id.title, title)
        rv.setTextViewText(R.id.text, msg)

        val builder = NotificationCompat.Builder(this, getChannelId("my_channel_id", "My default Channel", "my_group_id", "My default Group"))
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.technotrack_24)
                .setTicker("Last china warning")
                .setAutoCancel(true)
                .setCustomContentView(rv)

        var defaults = 0
        defaults = defaults or Notification.DEFAULT_VIBRATE
        defaults = defaults or Notification.DEFAULT_SOUND

        builder.setDefaults(defaults)

        val nc = builder.build()

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(10, nc)
    }

    companion object {
        private val TAG = "MainActivity"

        var messages: MutableList<String> = ArrayList()
        private var count = 0
    }


}
