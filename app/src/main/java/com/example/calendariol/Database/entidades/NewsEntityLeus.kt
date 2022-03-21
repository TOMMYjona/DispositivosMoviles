package com.example.calendariol.Database.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "news")
@Serializable
//si yo necesito que se serialice se va a convertir en un archivo sencillo
//a nivel local
data class NewsEntityLeus(
    @PrimaryKey(autoGenerate = true )
    val id:Int=0,
    val autor:String?,
    val title:String?,
    val desc:String?,
    val img:String?,
    var url:String?,
    var cood:String?){

}

