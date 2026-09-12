package com.achinthas.infoflow.notificationView

import android.content.pm.PackageManager
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.AppWithNotifications
import com.achinthas.infoflow.R

class AppAdapter (private var apps : List<AppWithNotifications>) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>(){

    class AppViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val txtappName : TextView = itemView.findViewById<TextView>(R.id.txt_appName)

        val txt_time : TextView = itemView.findViewById<TextView>(R.id.txt_time)
        val appIcon : ImageView = itemView.findViewById<ImageView>(R.id.appIcon)

        val notificationRecycler : RecyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerNotifications)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)

        return AppViewHolder(view)

    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {


        val app = apps[position]


        holder.txtappName.text = app.app.appName

        val packageManager = holder.itemView.context.packageManager

        try {
            val icon = packageManager.getApplicationIcon(app.app.packageName)
            holder.appIcon.setImageDrawable(icon)
        } catch (e: PackageManager.NameNotFoundException) {
            holder.appIcon.setImageResource(R.drawable.baseline_android_24)
        }

        val notifications = app.notifications.sortedByDescending {
            it.postedTime
        }.take(5)

        val lastNotification = notifications.firstOrNull()


        holder.txt_time.text = lastNotification?.let {
            DateUtils.getRelativeTimeSpanString(
                it.postedTime,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            ).toString()
        } ?: ""



        holder.notificationRecycler.layoutManager =
            LinearLayoutManager(holder.itemView.context)

        holder.notificationRecycler.adapter =
            NotificationAdapter(notifications)

    }

    override fun getItemCount(): Int {
        return apps.size
    }

    fun updateData(newApps: List<AppWithNotifications>) {
        apps = newApps
        notifyDataSetChanged()
    }
}