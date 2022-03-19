import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.calendariol.Database.entidades.Users
import com.example.calendariol.databinding.ActivityRegisterBinding

import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class Register : Fragment() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var database:DatabaseReference
    private lateinit var Store:StorageReference
    private lateinit var folderref:StorageReference
    private lateinit var fotoref:StorageReference
    val CODE_GALLERY = 22
    lateinit var uriimg: Uri
    lateinit var dowload: Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View?
    {
        binding=  ActivityRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        binding.Registrar.setOnClickListener(){
            val firtname=binding.nombre.text.toString()
            val lastname=binding.apellido.text.toString()
            val age=binding.edad.text.toString()

            //------------------cargar imagen a firebase------------------------------------------
            Store= FirebaseStorage.getInstance().getReference()
            folderref=Store.child("Pets_IMG")
            fotoref=folderref.child(Date().toString())
            //------------------obtener la imagen de firebase y guardar en database---------------

            fotoref.putFile(uriimg).addOnSuccessListener { taskSnapshot ->
                val uriTask: Task<Uri> =taskSnapshot.getStorage().getDownloadUrl()
                while (!uriTask.isSuccessful);
                dowload=uriTask.getResult()
                val img=dowload.toString()
                database=FirebaseDatabase.getInstance().getReference("Users")
                val User=Users(firtname,lastname,age,img)
                database.child(firtname).setValue(User).addOnSuccessListener {
                    binding.nombre.text.clear()
                    binding.apellido.text.clear()
                    binding.edad.text.clear()


                    Toast.makeText(this.context,"Succesful Saved",Toast.LENGTH_SHORT).show()

                }.addOnFailureListener{
                    Toast.makeText(this.context,"Failed Saved",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e -> }




        }
        binding.ImagenFotos.setOnClickListener(){
            //intent llama a la clase donde se encuentra gallery
            val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult(intent,CODE_GALLERY)// inicializa el intent
            getResult.launch(intent)

        }

    }

    //-----------------------------------------------------------------------------------------------------
    val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
        if(result.resultCode == Activity.RESULT_OK){
            try {
                if(result.data!=null){
                    val data=result.data
                    uriimg=data!!.getData()!!
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(getActivity()?.getContentResolver(), uriimg)
                        binding.imgpets.setImageBitmap(bitmap)
                    }catch (e: ApiException){
                        e.printStackTrace()

                    }

                }

            }catch (e:ApiException){

            }



        }
    }



}