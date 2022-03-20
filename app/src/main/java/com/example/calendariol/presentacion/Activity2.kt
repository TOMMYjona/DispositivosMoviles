package com.example.calendariol.presentacion

import android.R.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.calendariol.databinding.Activity2Binding
import kotlinx.android.synthetic.main.activity_2.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class Activity2 : Fragment () {

    private lateinit var startTime: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var binding: Activity2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= Activity2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //Asociar evento clik al Editext de la fecha del recordatorio
        val d1 = binding.etDate
        d1.setOnClickListener {
            showDatePickerDialog()
        }

        var recordatorio= mutableListOf("Lista de Recordatorios")
        val fechas= mutableListOf("")
        val horas= mutableListOf("")
        //Asociar evento clik al botón para guardar recordatorios
        binding.btnRecordatorio.setOnClickListener(View.OnClickListener {

            //Se inicializa las listas que van a contener los valores ingresados por el usuario
            //for para recorrer los 10 espacios de la lista y por medio del if saber si la posición está vacía o no
            recordatorio.add(binding.etEvent.getText().toString())
            fechas.add(binding.etDate.getText().toString())
            horas.add(binding.etTime.getText().toString())
            //para ubicar los datos en la ListView
            val lista= binding.etList

            //REVISAR CAMBIÉ LO DEL ARRAYADAPTER
            val arrayAdapter = activity?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    recordatorio
                )
            }

            lista.adapter= arrayAdapter

            //Asociar evento de clik en cada item de la lista para ver la fecha y la hora
            lista.setOnItemClickListener(){adapterview,view,position,id->
                Toast.makeText(this.context,
                    "Fecha: " + fechas[position] + "Hora: " + horas[position],Toast.LENGTH_LONG).show()
            }
//            val sharpref = getPreferences(MODE_PRIVATE)
//            val recordatorio = sharpref.edit()
//            recordatorio.putString("Recordatorio", etEvent.getText().toString())
//            recordatorio.commit()
        })

        setUpStartTime()
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            val d1 = binding.etDate
            d1.setText(selectedDate)
        })
        val ft=(context as FragmentActivity).supportFragmentManager
        newFragment.show(ft,"datePicker")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpStartTime() {
        startTime = binding.etTime
        startTime.text = LocalTime.now().format(formatter)
        startTime.setOnClickListener {
            showStartTimePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showStartTimePicker() {
        val newFragment = TimePickerFragment.newInstance(TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            /*showDialog{ _, hour, minute -> */
            /*val currentTime = LocalTime.of(hour, minute)*/
            val currentTime = hour.toString() + ": " + minute.toString()

            val t1 = binding.etTime
            t1.setText(currentTime)
        })
        val ft=(context as FragmentActivity).supportFragmentManager
        newFragment.show(ft,"timePicker")


    }

    fun showDialog(observer: TimePickerDialog.OnTimeSetListener) {
        TimePickerFragment.newInstance(observer)
    }

}
