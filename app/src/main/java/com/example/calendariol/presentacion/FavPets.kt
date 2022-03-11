package com.example.calendariol.presentacion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendariol.controladores.adapters.NewsLeusAdapter
import com.example.calendariol.Database.entidades.NewsEntityLeus
import com.example.calendariol.databinding.FragmentFavPetsBinding
import com.example.calendariol.logica.NoticiasBLPets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FavPets : Fragment() {
    private lateinit var binding: FragmentFavPetsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavPetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        super.onStart()
        binding.progressBarFav.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.Main) {
            val items = withContext(Dispatchers.IO) {
                NoticiasBLPets().getFavoritesNews()
            }
            binding.listRecyclerViewFav.adapter =
                NewsLeusAdapter(items) { getNewsItem(it) }
            binding.listRecyclerViewFav.layoutManager =
                LinearLayoutManager(binding.listRecyclerViewFav.context)
            binding.progressBarFav.visibility = View.INVISIBLE
        }
    }

    private fun getNewsItem(news: NewsEntityLeus) {
        var i = Intent(activity, ItemActivityPets::class.java)
        val jsonString = Json.encodeToString(news)
        i.putExtra("noticia", jsonString)
        startActivity(i)
    }
}