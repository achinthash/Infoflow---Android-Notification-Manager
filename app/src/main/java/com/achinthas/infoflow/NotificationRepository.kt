package com.achinthas.infoflow

import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val notificationDao: NotificationDao) {

    suspend fun insertNotification(notification: NotificationEntity): Long{
        return notificationDao.insertNotification(notification)
    }

    fun getNotificationsByApp(appId: Long): Flow<List<NotificationEntity>>{
        return notificationDao.getNotificationsByApp(appId)
    }

    suspend fun markAsRead(notificationId: Long) {
        notificationDao.markAsRead(notificationId)
    }

    suspend fun deleteNotificationsByIds(ids: Set<Long>){
        notificationDao.deleteNotificationsByIds(ids)
    }


    suspend fun getTotalNotificationCount():Int{
        return notificationDao.getTotalNotificationCount()
    }

    suspend fun getTodayNotificationCount(startOfDay:Long):Int{
        return notificationDao.getTodayNotificationCount(startOfDay)
    }

    suspend fun deleteAllNotifications(){
        return notificationDao.deleteAllNotifications()
    }

    suspend fun getTodayUnreadCount(startOfDay: Long):Int{
        return notificationDao.getTodayUnreadCount(startOfDay)
    }

    suspend fun getLast7NotificationsCountByDay():List<DailyNotificationCount>{
        return notificationDao.getLast7NotificationsCountByDay()
    }

    suspend fun getLast7WeeksNotificationsCounts():List<WeeklyNotificationCount>{
        return notificationDao.getLast7WeeksNotificationsCounts()
    }
}