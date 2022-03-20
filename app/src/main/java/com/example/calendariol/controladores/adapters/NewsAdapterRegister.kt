package com.example.calendariol.controladores.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.ButtonBarLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendariol.Database.entidades.NewsEntityLeus
import com.example.calendariol.Database.entidades.Users
import com.example.calendariol.R
import com.example.calendariol.databinding.ItemPetsBinding
import com.example.calendariol.databinding.ItemPetsRegisterBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import java.lang.String.valueOf
import java.security.KeyRep
import kotlin.jvm.internal.Ref


class NewsAdapterRegister(private val userList:ArrayList<Users>):RecyclerView.Adapter<NewsAdapterRegister.RegisteviewHolder>(){

    private lateinit var database: DatabaseReference
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisteviewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_pets_register,parent,false)
        return RegisteviewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RegisteviewHolder, position: Int) {

        val currentitem=userList[position]
        holder.firtname.text=currentitem.firstname
        holder.lastname.text=currentitem.lastname
        holder.edad.text=currentitem.age
        Picasso.get().load(currentitem.img).into(holder.img);
        //-----------boton para eleminar datos de la mascota registrada-------------------------
        holder.buttonac.setOnClickListener(){
            database=FirebaseDatabase.getInstance().getReference("Users")
            database.child(valueOf(currentitem.firstname).toString()).removeValue()

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class RegisteviewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val firtname:TextView=itemView.findViewById(R.id.nombreR)
        val lastname:TextView=itemView.findViewById(R.id.apellidoR)
        val edad:TextView=itemView.findViewById(R.id.edadR)
        val img:ImageView=itemView.findViewById(R.id.imagenR)
        val buttonac:FloatingActionButton=itemView.findViewById(R.id.floatingActionButton)




    }

}
