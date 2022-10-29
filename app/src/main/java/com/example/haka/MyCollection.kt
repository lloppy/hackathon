package com.example.haka

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class MyCollection : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var name: String
    lateinit var data: ArrayList<String>

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)

        auth = Firebase.auth
        name = auth.currentUser!!.displayName.toString()

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener{
            val i = Intent(this, CreateCardActivity::class.java)
            startActivity(i)
        }
        data = ArrayList()

        val saveButton: Button = findViewById(R.id.save)
        saveButton.setOnClickListener{
            loadDataFromFirebase()
        }


    }

    private fun loadDataFromFirebase() {
        val reference = FirebaseDatabase.getInstance().reference.child(name)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val nameFb = dataSnapshot.toString()
                    val linkFb = dataSnapshot.toString()
                    val priceFb = dataSnapshot.key.toString()
                    Log.e("data", priceFb)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}