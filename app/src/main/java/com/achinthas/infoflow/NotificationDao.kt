package com.achinthas.infoflow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Insert
    suspend fun insertNotification(notification: NotificationEntity): Long

//    @Query("SELECT * FROM notifications ORDER BY postedTime DESC")
//    fun getAllNotifications(): List<Notification>



    // Notifications belonging to one app
    @Query("""SELECT * FROM notifications WHERE appId = :appId ORDER BY postedTime DESC """)
    fun getNotificationsByApp(
        appId: Long
    ): Flow<List<NotificationEntity>>

    @Query("Update notifications SET isRead = 1 WHERE id = :notificationId")
    fun markAsRead(notificationId: Long)

    @Query("DELETE FROM notifications WHERE id IN (:ids) ")
    suspend fun deleteNotificationsByIds(ids: Set<Long>)


    @Query("SELECT COUNT(*) FROM notifications")
    suspend fun getTotalNotificationCount(): Int

    @Query("""SELECT COUNT(*) FROM notifications WHERE postedTime >= :startOfDay""")
    suspend fun getTodayNotificationCount(startOfDay: Long):Int

    @Query("DELETE FROM notifications")
    suspend fun deleteAllNotifications()

    @Query("""SELECT COUNT(*) FROM notifications WHERE postedTime >= :startOfDay AND isRead = 0""" )
    suspend fun getTodayUnreadCount(startOfDay: Long):Int

    @Query("""SELECT date(postedTime / 1000, 'unixepoch', 'localtime') AS date,
        COUNT(*) AS count  
        FROM notifications
        WHERE postedTime >= strftime('%s', 'now', '-6 days') * 1000
        GROUP BY date
        ORDER BY date ASC""")
    suspend fun getLast7NotificationsCountByDay(): List<DailyNotificationCount>


    @Query("""
        SELECT 
            strftime('%Y-%W', postedTime / 1000, 'unixepoch', 'localtime') AS year_week,
            MIN(DATE(postedTime / 1000, 'unixepoch', 'localtime')) AS week_start_date,
            COUNT(*) AS count
        FROM notifications
        WHERE postedTime >= strftime('%s', 'now', '-49 days') * 1000
        GROUP BY year_week
        ORDER BY year_week ASC
        LIMIT 7
    """)
    suspend fun getLast7WeeksNotificationsCounts(): List<WeeklyNotificationCount>

}