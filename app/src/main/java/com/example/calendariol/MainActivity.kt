package com.example.calendariol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button: Button = findViewById(R.id.btnR)
        button.setOnClickListener {
            // Do something in response to button click
            var lanzar = Intent(this, Activity2 :: class.java)
            startActivity(lanzar)
        }

        val bp: Button = findViewById(R.id.btnpreba)
        bp.setOnClickListener {
            // Do something in response to button click
            var lanzar = Intent(this, Inicio :: class.java)
            startActivity(lanzar)
        }

    }
}

