package com.example.calendariol

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class Activity2 : AppCompatActivity () {

    private lateinit var startTime: TextView
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        //Asociar evento clik al Editext de la fecha del recordatorio
        val d1 = findViewById<EditText>(R.id.etDate)
        d1.setOnClickListener {
            showDatePickerDialog()
        }


        setUpStartTime()
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment.newInstance(DatePickerDialog.OnDateSetListener { _, year, month, day ->
            // +1 because January is zero
            val selectedDate = day.toString() + " / " + (month + 1) + " / " + year
            val d1 = findViewById<EditText>(R.id.etDate)
            d1.setText(selectedDate)
        })

        newFragment.show(supportFragmentManager, "datePicker")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpStartTime() {
        startTime = findViewById<EditText>(R.id.etTime)
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

            val t1 = findViewById<EditText>(R.id.etTime)
            t1.setText(currentTime)
        })
        newFragment.show(supportFragmentManager,"timePicker")

    }


    fun showDialog(observer: TimePickerDialog.OnTimeSetListener) {
        TimePickerFragment.newInstance(observer)
            .show(supportFragmentManager, "time-picker")
    }


}