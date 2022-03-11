package com.example.calendariol.utilsPets

import android.app.Application
import androidx.room.Room
import com.example.calendariol.Database.NewsDataBase

class Leus:Application() {
    companion object {
        private var db: NewsDataBase? = null

        fun getDatabase(): NewsDataBase {
            return db!!
        }

    }

    //va ha ser accedida por cualquier parte del proyecto
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(applicationContext, NewsDataBase::class.java, "news_DB")
            .build()


    }

}