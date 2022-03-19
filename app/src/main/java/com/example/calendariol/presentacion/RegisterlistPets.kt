package com.example.calendariol.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calendariol.Database.entidades.Users
import com.example.calendariol.R
import com.example.calendariol.controladores.adapters.NewsAdapterRegister
import com.example.calendariol.databinding.FragmentListarNewsDogsBinding
import com.example.calendariol.databinding.FragmentRegisterlistPetsBinding
import com.google.firebase.database.*

class RegisterlistPets : Fragment() {
    private lateinit var binding: FragmentRegisterlistPetsBinding
    private lateinit var dbref:DatabaseReference
    private lateinit var userrecycler:ArrayList<Users>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=  FragmentRegisterlistPetsBinding.inflate(inflater, container, false)
        return binding.root
    }
    //-----------------------------------------------------------------------------
    override fun onStart() {
        super.onStart()
        binding.listrecyclerViewpets.layoutManager=LinearLayoutManager(binding.listrecyclerViewpets.context)
        binding.listrecyclerViewpets.setHasFixedSize(true)
        userrecycler= arrayListOf<Users>()
        getuserdata()

    }
    //-----------------------------------------------------------------------------
    private fun getuserdata() {
        dbref=FirebaseDatabase.getInstance().getReference("Users")
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (usersnapshot in snapshot.children){
                        val users=usersnapshot.getValue(Users::class.java)
                        userrecycler.add(users!!)
                    }
                    binding.listrecyclerViewpets.adapter=NewsAdapterRegister(userrecycler)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}