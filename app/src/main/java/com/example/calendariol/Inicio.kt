package com.example.calendariol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.calendariol.databinding.ActivityInicioBinding
import com.example.calendariol.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_inicio.*

class Inicio : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)//no es recomendable por el manejo de datos
        setTheme(R.style.Theme_CalendarioL)
        super.onCreate(savedInstanceState)
        binding= ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Analytics Event
        val anlytics= FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message","integracion de Firebase completa")
        anlytics.logEvent("InitScreen",bundle)
        //setup
        setup()

    }
    private fun setup(){
        title="Autentification"
        binding.etregistro.setOnClickListener(){
            if (binding.loginTxt2.text.isNotEmpty() && binding.loginTxt3.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.loginTxt2.text.toString(),binding.loginTxt3.text.toString()).addOnCompleteListener(){
                        if(it.isSuccessful){
                            showhome(it.result?.user?.email?:"",ProviderType.BASIC)

                        }else{
                            showalert()
                        }
                }

            }
        }
        binding.etiniciar.setOnClickListener(){
            if (binding.loginTxt2.text.isNotEmpty() && binding.loginTxt3.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.loginTxt2.text.toString(),binding.loginTxt3.text.toString()).addOnCompleteListener(){
                    if(it.isSuccessful){
                        showhome(it.result?.user?.email?:"",ProviderType.BASIC)

                    }else{
                        showalert()
                    }
                }

            }

        }
    }

    private fun showalert(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ah producido un error autentificacion al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }

    private fun showhome(email:String,provider:ProviderType){
        val Mainintent=Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(Mainintent)
    }
}