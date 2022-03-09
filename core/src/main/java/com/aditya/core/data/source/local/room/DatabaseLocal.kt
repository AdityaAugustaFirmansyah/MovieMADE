package com.aditya.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aditya.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2, exportSchema = false)
abstract class DatabaseLocal : RoomDatabase() {
    abstract fun movieDao(): MovieDao

}