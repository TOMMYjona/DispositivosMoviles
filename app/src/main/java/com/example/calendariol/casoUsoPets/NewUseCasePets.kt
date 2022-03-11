package com.example.calendariol.casoUsoPets

import com.example.calendariol.data.Database.entidades.NewsEntityLeus
import com.example.calendariol.utilsPets.Leus

class NewUseCasePets {
    private val newlist= listOf<NewsEntityLeus>(
        NewsEntityLeus(  1,"Sarah Perez",
            "This Week in Apps: Microsoft's app store principles, TikTok's new safety policies, Apple reveals 'Tap to Pay'",
            "Welcome back to This Week in Apps, the weekly TechCrunch series that recaps the latest in mobile OS news, mobile applications and the overall app economy. The app industry continues to grow, with a record number of downloads and consumer spending across both …",
            "https://techcrunch.com/wp-content/uploads/2021/11/this-week-in-apps-splash-2021.png?w=753"),

        NewsEntityLeus(2,"Alex Wilhelm",
            "Soon all blockchain companies will be crypto speculators",
            "Welcome to The TechCrunch Exchange, a weekly startups-and-markets newsletter. It’s inspired by the daily TechCrunch+ column where it gets its name. Want it in your inbox every Saturday? Sign up here.  It’s a little hard to sit down and write up some jaunty no…",
            "https://techcrunch.com/wp-content/uploads/2020/06/NSussman_Techcrunch_Exchange-multicolor.jpg?w=533",),

        NewsEntityLeus(3,"Krystal1222222222",
            "'Take a chill pill, stay long' — Anthony Scaramucci says bitcoin's recent plunge won't last",
            "The SkyBridge Capital founder told CNBC on Tuesday he advises his own clients to invest in cryptocurrency, but without getting overexcited.",
            "https://depor.com/resizer/4LiA3UcZpkTbq0pGF8j9dPiahkw=/580x330/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/6Y2EDIISGFGVFANEVDCR5LCG34.jpg"
        )
    )
    fun getAllNews():List<NewsEntityLeus>{
        return newlist
    }
    //debe ser suspendida y pueda correr sobre una corrutina
    suspend fun getFavoritesNews(): List<NewsEntityLeus> {
        val db = Leus.getDatabase()
        return db.newsDao().getAllNews()
    }

    suspend fun saveNewFavNews(news: NewsEntityLeus) {
        Leus.getDatabase().newsDao().insertNews(news)
    }

    suspend fun deleteNewFavNews(news: NewsEntityLeus) {
        Leus.getDatabase().newsDao().deleteNewsById(news.id)
    }

    suspend fun getOneNews(id: Int): NewsEntityLeus {
        return Leus.getDatabase().newsDao().getNewsById(id)
    }
}