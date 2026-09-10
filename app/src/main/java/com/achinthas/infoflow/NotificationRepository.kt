package com.achinthas.infoflow

import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val notificationDao: NotificationDao) {

    suspend fun insertNotification(notification: NotificationEntity): Long{
        return notificationDao.insertNotification(notification)
    }

    fun getNotificationsByApp(appId: Long): Flow<List<NotificationEntity>>{
        return notificationDao.getNotificationsByApp(appId)
    }
}