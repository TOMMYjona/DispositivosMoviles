package com.example.calendariol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.calendariol.databinding.ActivityMainBinding
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private lateinit var toogle:ActionBarDrawerToggle
    private lateinit var binding:ActivityMainBinding
    private var lsFragments= mutableListOf<Int>()//constante global como variable mutable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toogle=ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open_drawer,R.string.close_drawer)
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navigation.setNavigationItemSelectedListener {item->
            when(item.itemId){
                R.id.inbox_item -> {
                    createfragment(prueba())
                    lsFragments.add(R.id.inbox_item)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.outbox_item->{
                    Toast.makeText(this, "seleccion Inicio", Toast.LENGTH_LONG).show()
                    var intent=Intent(this, Inicio::class.java)//para navegar entre aplicaciones
                    startActivity(intent)// inicializa el intent
                    lsFragments.add(R.id.outbox_item)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.favourites_item->{
                    Toast.makeText(this, "seleccion Date", Toast.LENGTH_LONG).show()
                    var intent=Intent(this, Activity2::class.java)//para navegar entre aplicaciones
                    startActivity(intent)// inicializa el intent
                    lsFragments.add(R.id.favourites_item)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else->false
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun createfragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.fragmeLayout.id,fragment)
            addToBackStack(null)
        }.commit()
    }


}

