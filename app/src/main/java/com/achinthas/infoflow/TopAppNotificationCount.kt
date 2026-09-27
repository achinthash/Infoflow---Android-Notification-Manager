package com.achinthas.infoflow

data class TopAppNotificationCount(
    val packageName: String,
    val appName: String?,
    val count: Int
)
