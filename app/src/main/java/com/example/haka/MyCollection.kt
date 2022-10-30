package com.example.haka

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


    class MyCollection : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var name: String
    lateinit var data: ArrayList<String>
    lateinit var link: ArrayList<String>

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var storage: FirebaseStorage
    private val TAG = "FirebaseImageLoader"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)

        auth = Firebase.auth
        name = auth.currentUser!!.displayName.toString()
        storage = Firebase.storage

        //var dataAdd = intent.extras!!.getString("dataAdd")

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
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
        val reference = FirebaseDatabase.getInstance().reference.child("UkulelesshopSrv")

        reference.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {

                    val text = findViewById<TextView>(R.id.text)

                    text.text = "${dataSnapshot.key.toString()} ${dataSnapshot.value.toString()}"
                    val path = dataSnapshot.key.toString()
                    val picture = findViewById<ImageView>(R.id.picture)

                    storage = Firebase.storage


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
    private fun displayImageRef(imageRef: StorageReference?, view: ImageView){
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.setImageBitmap(bmp)
        }?.addOnFailureListener {  }
    }
}
