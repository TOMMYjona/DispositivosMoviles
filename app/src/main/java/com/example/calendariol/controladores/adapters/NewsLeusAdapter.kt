package com.example.calendariol.controladores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendariol.R
import com.example.calendariol.data.Database.entidades.NewsEntityLeus
import com.example.calendariol.databinding.ItemPetsBinding
import com.example.calendariol.presentacion.ItemActivityPets
import com.squareup.picasso.Picasso

class NewsLeusAdapter(val newsItemList:List<NewsEntityLeus>,val onClickItemSelected:(NewsEntityLeus)->Unit):
    RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var layoutInflater= LayoutInflater.from(parent.context)//variable que va inflar convertir los xml, las etiquetas en objetos
        val view=layoutInflater.inflate(R.layout.item_pets,parent,false)//necesitamos que vamos a inflar xml, contexto el cual a inflar, un booleano
        return NewsViewHolder(view)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item=newsItemList[position]//ya se tiene el item
        holder.render(item,onClickItemSelected)

    }

    override fun getItemCount(): Int=newsItemList.size


}

//contruida las clases y vinvuladas entre si
//Clase que le pinta
class NewsViewHolder (newsView: View) : RecyclerView.ViewHolder(newsView){
    //toca diseñar la vista list_new
    //funcion que une la programacion con la vista

    private val binding:ItemPetsBinding= ItemPetsBinding.bind(newsView) //vinvula bind

    //
    fun render(item:NewsEntityLeus, onClickItemSelected:(NewsEntityLeus)->Unit ){
        binding.textitulo.text=item.title
        Picasso.get().load(item.img).into(binding.imageView3);

        itemView.setOnClickListener(){
            //Toast.makeText(binding.imageView6.context,item.title,Toast.LENGTH_SHORT).show()
            onClickItemSelected(item)
        }

    }

}
