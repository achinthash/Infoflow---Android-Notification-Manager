package com.achinthas.infoflow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertApp(app: AppEntity): Long

    @Query("SELECT * FROM apps")
    fun getAllApps(): List<AppEntity>

    @Query("SELECT * FROM apps WHERE packageName = :packageName LIMIT 1")
    suspend fun getAppByPackageName(packageName: String): AppEntity?

    @Query(""" SELECT * FROM apps ORDER BY appName ASC""")
    @Transaction
    fun getAllAppsWithNotifications(): Flow<List<AppWithNotifications>>
}