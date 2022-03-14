package com.example.calendariol.presentacion

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.calendariol.R
import com.example.calendariol.databinding.ActivityInicioBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_inicio.*

class Inicio : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    //constante para google
    private val GOOGLE_SIGN_IN=100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Thread.sleep(2000)//no es recomendable por el manejo de datos
        setTheme(R.style.Theme_CalendarioL)
        //Analytics Event
        val anlytics= FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message","integracion de Firebase completa")
        anlytics.logEvent("InitScreen",bundle)
        //setup
        setup()
        //verifica si existe una sesion activa
        session()

    }

    override fun onStart() {
        super.onStart()
        auntlayout.visibility=View.VISIBLE
    }
    private fun session(){
        val prefs=getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)//ficha de preferencias
        val email=prefs.getString("email",null)
        val provider=prefs.getString("provider",null)
        if (email!=null && provider!=null){
            auntlayout.visibility=View.INVISIBLE//se pone invesible si exite una session iniciada
            showhome(email,ProviderType.valueOf(provider))
        }
    }
    private fun setup(){
        title="Autentification"
        binding.etregistro.setOnClickListener(){
            if (binding.loginTxt2.text.isNotEmpty() && binding.loginTxt3.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.loginTxt2.text.toString(),binding.loginTxt3.text.toString()).addOnCompleteListener(){
                        if(it.isSuccessful){
                            showhome(it.result?.user?.email?:"", ProviderType.BASIC)

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
                        showhome(it.result?.user?.email?:"", ProviderType.BASIC)

                    }else{
                        showalert()
                    }
                }

            }

        }
        binding.GoogleButton.setOnClickListener(){
            //configuracion
            val googleconf=
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build()

            val googleclient=GoogleSignIn.getClient(this,googleconf)
            googleclient.signOut()
            //startActivityForResult(googleclient.signInIntent,GOOGLE_SIGN_IN)
            getResult.launch(googleclient.signInIntent)
        }
    }
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            if(result.resultCode == Activity.RESULT_OK){
                val task=GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account=task.getResult(ApiException::class.java)
                    if(account!=null){//si la cuenta es distinta de nula se recupera la credencial de firebase
                        val credential=GoogleAuthProvider.getCredential(account.idToken,null)
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{
                            if(it.isSuccessful){
                                showhome(account.email?:"",ProviderType.GOOGLE)
                            }else{
                                showalert()
                            }

                        }
                    }

                }catch (e:ApiException){
                    showalert()
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

    private fun showhome(email:String,provider: ProviderType){
        val Mainintent=Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(Mainintent)
    }

}