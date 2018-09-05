package com.codingexercise.verve.stevencontreras.weatherlocationapp.data.repository.locationprovider.android

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationCompat.PRIORITY_DEFAULT
import com.codingexercise.verve.stevencontreras.weatherlocationapp.R
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.Event
import com.codingexercise.verve.stevencontreras.weatherlocationapp.domain.model.location.event.LocationEvent
import io.reactivex.ObservableEmitter

class FGS: Service() {
    companion object {
        private val ASNB_NOTIFICATION_ID = 8852
        private val ASNB_CHANNEL_ID = Service::class.java.`package`.name + ".service"
    }

    private lateinit var androidLocationUpdatesManager: AndroidLocationUpdatesManager
    var observabLocationEventEmitter: ObservableEmitter<LocationEvent>? = null

    override fun onBind(intent: Intent?): IBinder? {
        return FGSBinder(this)
    }

    fun start_explicit() {
        startForeground(
            ASNB_NOTIFICATION_ID,
            fgs_asn__prepare(getString(R.string.app_name), getString(R.string.stopped))
        )
        observabLocationEventEmitter!!.onNext(LocationEvent(event = Event.SERVICE_RUNNING))

        //now we want to use Android's Location Manager to get location updates
        androidLocationUpdatesManager = AndroidLocationUpdatesManager(applicationContext, observabLocationEventEmitter!!)
        if (!androidLocationUpdatesManager.start_updates())
            stop_explicit()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start_explicit()
        return Service.START_REDELIVER_INTENT
    }

    private fun getNotificationChannelId(): String {
        var channelId = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = ASNB_CHANNEL_ID
            val channelName = "FGS.ASNB"
            val chan = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
            chan.lightColor = Color.BLUE
            chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(chan)
        }
        return channelId
    }

    private fun fgs_asn__prepare(s_title: String, s_status: String): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, getNotificationChannelId())
        return notificationBuilder
            .setPriority(PRIORITY_DEFAULT)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentTitle(s_title)
            .setContentText(s_status)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setSmallIcon(R.drawable.ic_satellite_black_24dp)
            .setOngoing(true)
            .build()
    }

    private fun fgs_asn__update(s_status: String) {
        val asn_mgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        asn_mgr.notify(ASNB_NOTIFICATION_ID, fgs_asn__prepare(getString(R.string.app_name), s_status))
    }

    fun stop_explicit() {
        stopSelf()
        observabLocationEventEmitter!!.onNext(LocationEvent(event = Event.SERVICE_STOPPED))
    }

    override fun onDestroy() {
        stop_explicit()
        observabLocationEventEmitter!!.onComplete()
        super.onDestroy()
    }
}