package com.example.calendariol.presentacion

import Register
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils.replace
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.calendariol.R
import com.example.calendariol.databinding.ActivityMainBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.util.*


enum class ProviderType{ //designa como es el metodo de login sea correo o google
    BASIC,
    GOOGLE
}
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toogle:ActionBarDrawerToggle
    private lateinit var binding:ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var drawer:DrawerLayout
    private var lsFragments= mutableListOf<Int>()//constante global como variable mutable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Analytics Event
        val anlytics=FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message","integracion de Firebase completa")
        anlytics.logEvent("InitScreen",bundle)
        //nuevo----------------------------------------------------------------------
        val toolbar2:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbarmain)
        //setSupportActionBar(toolbar2)
        //------------------------------------------------------------------------------
        drawer=binding.drawerLayout
        toogle=ActionBarDrawerToggle(this,drawer,toolbar2,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawer.addDrawerListener(toogle)
        //nuevo-----------------------------------------------------------------

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)//nuevo

        val navigationView:NavigationView=binding.navigation
        navigationView.setNavigationItemSelectedListener(this)
        //------------------------------------------------------------------
        //toogle.syncState()

        //borrado datos firebase
        createfragment(R.id.Noticias,ListarFragmentPets())


        //setup
        val bundle2=intent.extras
        val email=bundle2?.getString("email")
        val provider=bundle2?.getString("provider")

        setup(email?:"",provider?:"")

        //Guardar el estado actual del usuario identificado

        val prefs=getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()//ficha de preferencias
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()





    }
    //---------------------------------------------------------------------------

     override fun onNavigationItemSelected(item:MenuItem):Boolean{

            when(item.itemId){
                R.id.Noticias -> {
                    Toast.makeText(this, "Seleccion Noticias", Toast.LENGTH_LONG).show()
                    createfragment(R.id.Noticias,ListarFragmentPets())
                    lsFragments.add(R.id.Noticias)

                }
                R.id.Favoritos ->{
                    Toast.makeText(this, "Seleccion Favoritos", Toast.LENGTH_LONG).show()
                    createfragment(R.id.Favoritos,FavPets())
                    lsFragments.add(R.id.Favoritos)

                }
                R.id.Pets ->{
                    Toast.makeText(this, "Seleccion Pets", Toast.LENGTH_LONG).show()
                    createfragment(R.id.Pets,RegisterlistPets())
                    lsFragments.add(R.id.Pets)

                }
                R.id.Creación_Usuarios->{
                    Toast.makeText(this, "Seleccion Usuarios", Toast.LENGTH_LONG).show()
                    createfragment(R.id.Creación_Usuarios,Register())
                    lsFragments.add(R.id.Creación_Usuarios)

                }
                R.id.Recordatorio->{
                    Toast.makeText(this, "Seleccion Usuarios", Toast.LENGTH_LONG).show()
                    createfragment(R.id.Recordatorio,Activity2())
                    lsFragments.add(R.id.Recordatorio)

                }
                R.id.Sigout->{
                    //borra los datos de inicio de sesion
                    val prefs=getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()//ficha de preferencias
                    prefs.clear()
                    prefs.apply()

                    FirebaseAuth.getInstance().signOut()
                    //val inicio=Intent(this , Inicio::class.java)
                    onBackPressed()//vuelve a la pantalla anterior
                    //startActivity(inicio)

                }


            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            return true

    }

    //-----------------------------------------------------------------------------------
    private fun setup(email:String,provider:String){
        //menuView correo y provider
        val navigationView:NavigationView=binding.navigation
        val hView: View = navigationView.getHeaderView(0)
        val correo = hView.findViewById<View>(R.id.nav_header_email) as TextView
        val providerContent = hView.findViewById<View>(R.id.nav_header_provider) as TextView
        correo.setText(email)
        providerContent.setText(provider)
        navigationView.setNavigationItemSelectedListener(this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    //----------------------------------------------------------
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }
    //-------------------------------------------------------------
    fun createfragment(tagToChange:Int,fragment: Fragment?=null){
        var addStack:Boolean=false
        val ft=supportFragmentManager.beginTransaction()
        if (lsFragments.isNotEmpty()){
            val currentFragment=
                supportFragmentManager.findFragmentByTag(lsFragments.last().toString())!!
            val toChangeFragment=supportFragmentManager.findFragmentByTag(tagToChange.toString())
            if (toChangeFragment!=null){
                if (currentFragment!=toChangeFragment){
                    addStack=true
                    ft.hide(currentFragment).show(toChangeFragment)
                }
            }else{
                if (fragment!=null){
                    addStack=true
                    ft.hide(currentFragment).add(binding.fragmeLayout.id,fragment,tagToChange.toString())
                }
            }
        }else{
            if (fragment!=null){
                ft.add(binding.fragmeLayout.id,fragment,tagToChange.toString())
                addStack=true

            }
        }
        if (addStack){
            ft.commit()
            ft.addToBackStack(tagToChange.toString())
            lsFragments.add(tagToChange)
        }

    }

    //funcion borrado
    fun deletedata(username:String){
        database=FirebaseDatabase.getInstance().getReference("Users")
        database.child(username).removeValue().addOnSuccessListener {
            Toast.makeText(this,"Sussesfull delete",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show()
        }

    }

}

