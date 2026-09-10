package com.achinthas.infoflow

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "notifications",
    foreignKeys = [
        ForeignKey(
            entity = AppEntity::class,
            parentColumns = ["id"],
            childColumns = ["appId"],
            onDelete = ForeignKey.CASCADE

        )
    ],
    indices = [
        Index(value = ["appId"])
    ]
)

data class NotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val appId: Long,

    val notificationKey: String,

    val title: String?,

    val text: String?,

    val bigTitle: String?,

    val bigText: String?,

    val subText: String?,

    val category: String?,

    val channelId: String?,

    val postedTime: Long,

    val bigPicture: String?,

    val largeIcon: String?,

    val isOngoing: Boolean,

    val isRead: Boolean = false,

    val isRemoved: Boolean = false
)