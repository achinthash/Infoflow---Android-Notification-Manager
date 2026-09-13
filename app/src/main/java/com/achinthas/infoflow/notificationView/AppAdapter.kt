package com.achinthas.infoflow.notificationView

import android.content.Intent
import android.content.pm.PackageManager
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.AppWithNotifications
import com.achinthas.infoflow.R

class AppAdapter(private var apps: List<AppWithNotifications>) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    class AppViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtappName: TextView = itemView.findViewById(R.id.txt_appName)
        val txt_time: TextView = itemView.findViewById(R.id.txt_time)
        val appIcon: ImageView = itemView.findViewById(R.id.appIcon)

        val notificationsContainer : LinearLayout = itemView.findViewById<LinearLayout>(R.id.notificationsContainer)

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

        val notifications = app.notifications.sortedByDescending { it.postedTime }.take(4)
        val lastNotification = notifications.firstOrNull()

        holder.txt_time.text = lastNotification?.let {
            DateUtils.getRelativeTimeSpanString(
                it.postedTime,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS
            ).toString()
        } ?: ""

        holder.notificationsContainer.removeAllViews()

        val inflater = LayoutInflater.from(holder.itemView.context)

        for(notification in notifications) {
            val childView = inflater.inflate(
                R.layout.item_app_with_notification,
                holder.notificationsContainer,
                false
            )

            val txtTitle = childView.findViewById<TextView>(R.id.txt_title)
            val txtText = childView.findViewById<TextView>(R.id.txt_text)

            txtTitle.text = notification.title ?: ""
            txtText.text = notification.text ?: ""

            holder.notificationsContainer.addView(childView)
        }

        // navigate notifications list page
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            val intent = Intent(context, NotificationsList::class.java).apply {
                putExtra("EXTRA_APP_ID", app.app.id)
                putExtra("EXTRA_PACKAGE_NAME", app.app.packageName)
                putExtra("EXTRA_APP_NAME", app.app.appName)
            }


            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = apps.size

    fun updateData(newApps: List<AppWithNotifications>) {
        apps = newApps
        notifyDataSetChanged()
    }
}