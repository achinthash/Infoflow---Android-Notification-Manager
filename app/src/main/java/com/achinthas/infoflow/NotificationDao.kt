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

}