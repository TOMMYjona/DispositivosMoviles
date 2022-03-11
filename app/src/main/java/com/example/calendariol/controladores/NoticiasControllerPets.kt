package com.example.calendariol.controladores

import com.example.calendariol.data.Database.entidades.NewsEntityLeus
import com.example.calendariol.logica.NoticiasBLPets

class NoticiasControllerPets {
    suspend fun saveFavNews(news: NewsEntityLeus) {
        NoticiasBLPets().saveNewFavNews(news)
    }

    suspend fun deleteFavNews(news: NewsEntityLeus) {
        NoticiasBLPets().deleteNewFavNews(news)
    }
}