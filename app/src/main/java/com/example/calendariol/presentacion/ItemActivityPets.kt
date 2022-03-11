package com.example.calendariol.presentacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.calendariol.R
import com.example.calendariol.controladores.NoticiasControllerPets
import com.example.calendariol.data.Database.entidades.NewsEntityLeus
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityItemPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var n:NewsEntityLeus?=null
        intent.extras?.let{
            n= Json.decodeFromString<NewsEntityLeus>(it.getString("noticia").toString())

        }
        if(n!=null){
            loadNews(n!!)
        }
        binding.floatingAction.setOnClickListener() {
            saveFavNews(n)
        }
    }
    fun loadNews(news:NewsEntityLeus){
        binding.textView7.text=news.title
        binding.textView5.text=news.desc
        binding.textView8.text=news.autor
        Picasso.get().load(news.img).into(binding.imageView4);
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