package com.achinthas.infoflow

import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDao: AppDao) {

    suspend fun insertApp(app: AppEntity): Long{
        return appDao.insertApp(app)
    }

    fun getAllApps():List<AppEntity>{
        return appDao.getAllApps()
    }

    suspend fun getOrCreateApp(packageName: String, appName: String) : AppEntity {

        val existingApp = appDao.getAppByPackageName(packageName)

        if (existingApp != null) {
            return existingApp
        }

        val newApp = AppEntity(
            packageName = packageName,
            appName = appName
        )

        val generatedId = appDao.insertApp(newApp)

        if (generatedId == -1L) {
            return appDao.getAppByPackageName(packageName)
                ?: error("insertApp : $packageName ")
        }

        return newApp.copy(id = generatedId)
    }

    fun getAllAppsWithNotifications(): Flow<List<AppWithNotifications>>{
        return appDao.getAllAppsWithNotifications()
    }
}