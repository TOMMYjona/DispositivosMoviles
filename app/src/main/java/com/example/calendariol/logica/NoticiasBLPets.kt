package com.example.calendariol.logica

import com.example.calendariol.casoUsoPets.NewUseCasePets
import com.example.calendariol.data.Database.entidades.NewsEntityLeus

class NoticiasBLPets (){
    suspend fun getNewList():List<NewsEntityLeus>{

        Thread.sleep(3000)

        return NewUseCasePets().getAllNews()
    }


    suspend fun getOneNews():NewsEntityLeus{
        val r=(0..2).random()
        return NewUseCasePets().getAllNews()[r]//regresa una noticia aleatoria dependiendo del valor de n

    }
    suspend fun checkIsSaved(id: Int): Boolean {
        val n = NewUseCasePets().getOneNews(id)
        return (n != null)
    }
    suspend fun saveNewFavNews(news: NewsEntityLeus) {
        NewUseCasePets().saveNewFavNews(news)
    }
    suspend fun getFavoritesNews(): List<NewsEntityLeus> {
        return NewUseCasePets().getFavoritesNews()
    }

    suspend fun deleteNewFavNews(news: NewsEntityLeus) {
        NewUseCasePets().deleteNewFavNews(news)
    }

}