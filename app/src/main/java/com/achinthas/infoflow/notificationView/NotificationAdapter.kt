package com.achinthas.infoflow.notificationView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.NotificationEntity
import com.achinthas.infoflow.R

class NotificationAdapter(private var notifications: List<NotificationEntity>
            ) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {


    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_title : TextView = itemView.findViewById<TextView>(R.id.txt_title)
        val txt_text : TextView = itemView.findViewById<TextView>(R.id.txt_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app_with_notification, parent, false)

        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
       val notification = notifications[position]

       holder.txt_title.text = notification.title ?: ""
        holder.txt_text.text = notification.text ?: ""

//         appWithNotification.notifications.forEach { notification ->
//             holder.txt_title.text = notification.title
//
//             holder.txt_text.text = notification.text
//        }



    }

    override fun getItemCount(): Int {
        return notifications.size
    }


//    fun updateData(newStudents: List<AppWithNotifications>) {
//        notifications = newStudents
//        notifyDataSetChanged()
//    }
}