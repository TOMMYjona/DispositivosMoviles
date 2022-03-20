package com.example.calendariol.presentacion

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.calendariol.R
import com.example.calendariol.controladores.NoticiasControllerPets
import com.example.calendariol.Database.entidades.NewsEntityLeus
import com.example.calendariol.databinding.ActivityItemPetsBinding
import com.example.calendariol.logica.NoticiasBLPets
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ItemActivityPets : AppCompatActivity() {
    private lateinit var binding: ActivityItemPetsBinding
    private var fav: Boolean = false
    var newsObtain: NewsEntityLeus? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        intent.extras?.let{
            newsObtain= Json.decodeFromString<NewsEntityLeus>(it.getString("noticia").toString())

        }
        if(newsObtain!=null){
            loadNews(newsObtain!!)
        }
        binding.floatingAction.setOnClickListener() {
            saveFavNews(newsObtain)
        }
        binding.googlepag.setOnClickListener(){
            googleurl(newsObtain!!)
        }
    }
    fun googleurl(news: NewsEntityLeus){
        val pag=Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
        startActivity(pag)
    }
    fun loadNews(news:NewsEntityLeus){
        binding.title.text=news.title
        binding.descp.text=news.desc
        binding.autor.text=news.autor
        Picasso.get().load(news.img).into(binding.contenimageFav);

        lifecycleScope.launch(Dispatchers.Main) {
            fav = withContext(Dispatchers.IO) { NoticiasBLPets().checkIsSaved(news.id) }
            if (fav) {
                binding.floatingAction.setImageResource(R.drawable.ic_favorite_24)
            }
        }
    }
    private fun saveFavNews(news: NewsEntityLeus?) {
        if (news != null) {
            if (!fav) {
                lifecycleScope.launch {
                    NoticiasControllerPets().saveFavNews(news)
                    binding.floatingAction.setImageResource(R.drawable.ic_favorite_24)
                }
            } else {
                lifecycleScope.launch {
                    NoticiasControllerPets().deleteFavNews(news)
                    binding.floatingAction.setImageResource(R.drawable.ic_favorite_border_12)
                }
            }
        }
    }
}