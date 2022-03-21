package com.example.calendariol.casoUsoPets

import com.example.calendariol.Database.entidades.NewsEntityLeus
import com.example.calendariol.utilsPets.Leus

class NewUseCasePets {
    private val newlist= listOf<NewsEntityLeus>(
        NewsEntityLeus(  1,"All Pets",
            "EMERGENCIAS / ATENCIÓN 24 HORAS",
            "El servicio de emergencia conlleva la estabilización del paciente y regularmente incluye la colocación de catéteres intravenosos, administración de fluidos vitales, medicación específica para el manejo del dolor, oxigeno terapia y en ciertos casos una transfusión sanguínea o de plasma.",
            "https://scontent.fuio2-1.fna.fbcdn.net/v/t31.18172-8/10856694_1578673822401008_1876686428446506567_o.jpg?stp=dst-jpg_s960x960&_nc_cat=103&ccb=1-5&_nc_sid=e3f864&_nc_ohc=eRzUqQPyf4EAX_ijkJU&_nc_ht=scontent.fuio2-1.fna&oh=00_AT8iUCjaS-TQOKHPoF1GU68zxSsXmfbNOehjbYIgN5d7Cw&oe=625CC749",
            "http://www.allpets-ec.com/",
            "geo:-0.18170597015701237,-78.478312992064"),


        NewsEntityLeus(2,"GDC Profesionales",
            "Clínica Veterinaria Pet Line",
            "Somos una empresa importadora y distribuidora de insumos veterinarios. Trabajamos con compañías internacionales localizadas en Brasil, EEUU y próximamente en Perú, Buscamos ampliar nuestro negocio implementando servicios para mascotas y sus dueños.",
            "https://container.aiyellow.com/pictures/3000000_3100000/3000000_3010000/3000000_3001000/3000300_3000400/3000336/banners/77ba5e225387fc5c5114cd9aab9ae54f.jpg",
            "https://profesionales.gdc.coop/ads/animalhelpveterinaria/",
            "geo:-0.20953838224732324,-78.39990760000002"),

        NewsEntityLeus(3,"PetLine",
            "Vet Solutions",
            "La línea Vet line busca contribuir con el desarrollo de los negocios veterinarios, importando, distribuyendo y exportando productos innovadores confiables y de alta calidad.",
            "https://vetsolutionsec.com/wp-content/uploads/2018/09/vet2.jpg",
            "https://vetsolutionsec.com/",
            "geo:-0.16525195430196227,-78.46801406137598")
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