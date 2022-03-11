package com.example.calendariol.Database.Dao

import androidx.room.*
import com.example.calendariol.data.Database.entidades.NewsEntityLeus

@Dao//etiqueta para manejar funciones en cuanto a bases de datos
interface NewsDaoPets {
    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<NewsEntityLeus>

    @Query("SELECT * FROM news WHERE id = :idNews")
    suspend fun getNewsById(idNews: Int): NewsEntityLeus

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntityLeus)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNews(news: NewsEntityLeus)

    @Delete()
    suspend fun deleteOneNews(news: NewsEntityLeus)

    @Query("DELETE FROM news")
    suspend fun cleanDbNews()

    @Query("DELETE FROM news WHERE id = :idNews")
    suspend fun deleteNewsById(idNews: Int)
}