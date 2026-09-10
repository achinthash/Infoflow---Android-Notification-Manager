package com.achinthas.infoflow

import androidx.room.Embedded
import androidx.room.Relation

data class AppWithNotifications(
    @Embedded val app: AppEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "appId"
    )
    val notifications : List<NotificationEntity>
)
