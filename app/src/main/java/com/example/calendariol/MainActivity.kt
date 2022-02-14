package com.example.calendariol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private lateinit var toogle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toogle=ActionBarDrawerToggle(this,drawerLayout,R.string.open_drawer,R.string.close_drawer)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigation.setNavigationItemSelectedListener {
            var lanzar = Intent(this, Activity2 :: class.java)
            var lanzar2 = Intent(this, Inicio :: class.java)
            when(it.itemId){
                R.id.inbox_item->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentcontainer,prueba())
                        startActivity(lanzar)
                        commit()
                    }
                }
                R.id.outbox_item->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragmentcontainer,prueba())
                        startActivity(lanzar2)
                        commit()
                    }
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }



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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}

