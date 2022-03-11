package com.example.calendariol.presentacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendariol.controladores.adapters.NewsLeusAdapter
import com.example.calendariol.data.Database.entidades.NewsEntityLeus
import com.example.calendariol.databinding.FragmentListarNewsDogsBinding
import com.example.calendariol.logica.NoticiasBLPets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ListarFragmentPets: Fragment()  {
    private lateinit var binding: FragmentListarNewsDogsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentListarNewsDogsBinding.inflate(inflater, container, false)   //inflar, ya no componente con componente sino inflartodo

        binding.progressBar2.visibility= View.VISIBLE

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.progressBar2.visibility= View.VISIBLE
        //corre en un hilo aparte de la corrutinas
        GlobalScope.launch(Dispatchers.Main) {
            val items= withContext(Dispatchers.IO){
                NoticiasBLPets().getNewList()}

            loadListNews(items)
        }
    }

    fun loadListNews(item: List<NewsEntityLeus>){
        binding.recyclerView.layoutManager=
            LinearLayoutManager(binding.recyclerView.context)//para que se liste con el liner layout los elementos
        binding.recyclerView.adapter=NewsLeusAdapter(item){getNewsItem(it)}
        binding.progressBar2.visibility= View.INVISIBLE
    }
    //funcion landa entre llaves {getNewsItem(it)} para el error it iterable

    fun getNewsItem(news: NewsEntityLeus){
        //Toast.makeText(binding.list.context,news.autor, Toast.LENGTH_SHORT).show()
        var i= Intent(activity,ItemActivityPets::class.java)
        val jsonString= Json.encodeToString(news)
        //deja envia objetos serializables
        i.putExtra("noticia",jsonString)
        startActivity(i)
    }

}