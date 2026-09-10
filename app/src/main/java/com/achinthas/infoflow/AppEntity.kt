package com.achinthas.infoflow

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "apps", indices = [
    Index(value = ["packageName"], unique = true)
] )

data class AppEntity (

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0 ,
    val packageName: String,
    val appName : String?

)