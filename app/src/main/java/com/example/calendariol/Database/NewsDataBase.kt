package com.example.calendariol.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calendariol.Database.Dao.NewsDaoPets
import com.example.calendariol.Database.entidades.NewsEntityLeus

@Database(
    entities = [NewsEntityLeus::class],
    version = 1
)
abstract class NewsDataBase:RoomDatabase() {
    abstract fun newsDao(): NewsDaoPets
}