package com.example.calendariol.Database.Dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calendariol.data.Database.entidades.NewsEntityLeus

@Database(
    entities = [NewsEntityLeus::class],
    version = 1
)
abstract class NewsDataBase:RoomDatabase() {
    abstract fun newsDao():NewsDaoPets
}