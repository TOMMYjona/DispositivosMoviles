package com.example.calendariol.controladores.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendariol.data.Database.entidades.NewsEntityLeus

class NewsLeusAdapter(val newsItemList:List<NewsEntityLeus>,val onClickItemSelected:(NewsEntityLeus)->Unit):
    RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

    }
}

//contruida las clases y vinvuladas entre si
//Clase que le pinta
class NewsViewHolder (newsView: View) : RecyclerView.ViewHolder(newsView){

}
