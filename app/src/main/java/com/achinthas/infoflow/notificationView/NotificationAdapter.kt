package com.achinthas.infoflow.notificationView

import android.app.Dialog
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.NotificationEntity
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationAdapter(private var notifications: List<NotificationEntity>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtTitle: TextView = itemView.findViewById(com.achinthas.infoflow.R.id.txt_title)

        val txtText: TextView = itemView.findViewById(com.achinthas.infoflow.R.id.txt_text)

        val imgLargeIcon: ImageView = itemView.findViewById(com.achinthas.infoflow.R.id.img_large_icon)

        val txtTime: TextView = itemView.findViewById(com.achinthas.infoflow.R.id.txt_time)

        val imgUnreadIndicator: ImageView = itemView.findViewById(com.achinthas.infoflow.R.id.img_unreadIndicator)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.achinthas.infoflow.R.layout.item_notification_list, parent,false)

        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NotificationViewHolder,
        position: Int
    ) {
        val notification = notifications[position]

        // Text
        holder.txtTitle.text = notification.title ?: ""
        holder.txtText.text = notification.text ?: ""

        // Read / unread
        if (notification.isRead) {
            holder.imgUnreadIndicator.visibility = View.GONE
        } else {
            holder.imgUnreadIndicator.visibility = View.VISIBLE
            holder.imgUnreadIndicator.setImageResource(com.achinthas.infoflow.R.drawable.baseline_unread_dot_24)
        }

        // Large image
        loadNotificationImage(holder.imgLargeIcon, notification.largeIcon)

        // Time
        holder.txtTime.text = formatSmartTime(notification.postedTime)


        // selected notification details

        holder.itemView.setOnClickListener {

            val context = holder.itemView.context

            val dialog = Dialog(context)

            dialog.setContentView(
                com.achinthas.infoflow.R.layout.dialog_notification_details
            )

            val title =
                dialog.findViewById<TextView>(com.achinthas.infoflow.R.id.dialogTitle)

            val time =
                dialog.findViewById<TextView>(com.achinthas.infoflow.R.id.dialogTime)

            val text =
                dialog.findViewById<TextView>(com.achinthas.infoflow.R.id.dialogText)

            val image =
                dialog.findViewById<ImageView>(com.achinthas.infoflow.R.id.dialogImage)

            val bigText =
                dialog.findViewById<TextView>(com.achinthas.infoflow.R.id.dialogBigText)

            val bigTitle =
                dialog.findViewById<TextView>(com.achinthas.infoflow.R.id.dialogBigTitle)

            val subText =
                dialog.findViewById<TextView>(com.achinthas.infoflow.R.id.dialogSubText)




            // Title
            title.text = notification.title ?: "No title"

            // Time
            time.text = SimpleDateFormat(
                "dd MMM yyyy, hh:mm a",
                Locale.getDefault()
            ).format(Date(notification.postedTime))



            // Text
            if (notification.text.isNullOrBlank()) {

                text.visibility = View.GONE

            } else {

                text.visibility = View.VISIBLE
                text.text = notification.text
            }

            // bigTitle
            if (notification.bigTitle.isNullOrBlank()) {

                bigTitle.visibility = View.GONE

            } else {

                bigTitle.visibility = View.VISIBLE
                bigTitle.text = notification.bigTitle
            }

            // subText
            if (notification.subText.isNullOrBlank()) {

                subText.visibility = View.GONE

            } else {

                subText.visibility = View.VISIBLE
                subText.text = notification.subText
            }


            // Big text
            if (notification.bigText.isNullOrBlank()) {

                bigText.visibility = View.GONE

            } else {

                bigText.visibility = View.VISIBLE
                bigText.text = notification.bigText
            }


            // Big picture
            if (!notification.bigPicture.isNullOrBlank()) {

                val file = File(notification.bigPicture)

                if (file.exists()) {

                    val bitmap =
                        BitmapFactory.decodeFile(file.absolutePath)

                    if (bitmap != null) {

                        image.visibility = View.VISIBLE
                        image.setImageBitmap(bitmap)

                    } else {

                        image.visibility = View.GONE
                    }

                } else {

                    image.visibility = View.GONE
                }

            } else {

                image.visibility = View.GONE
            }


            dialog.show()


            // Dialog width
            dialog.window?.setLayout(
                (context.resources.displayMetrics.widthPixels * 0.92).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
    }



    private fun loadNotificationImage(imageView: ImageView, filePath: String?) {

        if (filePath.isNullOrEmpty()) {
            imageView.setImageResource(com.achinthas.infoflow.R.drawable.baseline_android_24)
            return
        }

        val imageFile = File(filePath)

        if (!imageFile.exists()) {
            imageView.setImageResource(com.achinthas.infoflow.R.drawable.baseline_android_24)
            return
        }

        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(com.achinthas.infoflow.R.drawable.baseline_android_24)
        }
    }

    fun formatSmartTime(timestamp: Long): String {
        return if(DateUtils.isToday(timestamp)) {
            SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(timestamp))
        } else{
            SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(timestamp))
        }
    }



    override fun getItemCount(): Int = notifications.size

    fun updateData(newApps: List<NotificationEntity>) {
        notifications = newApps
        notifyDataSetChanged()
    }


}