package com.example.haka

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddActivity : AppCompatActivity() {
    val REQUEST_CODE = 100
    lateinit var auth: FirebaseAuth
    lateinit var name: String

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val nameInput = findViewById<EditText>(R.id.name)

        val imageView = findViewById<ImageView>(R.id.loadImageView)
        imageView.setOnClickListener{
            openGalleryForImage()
        }

        auth = Firebase.auth
        name = auth.currentUser!!.displayName.toString()

        val safeButton = findViewById<Button>(R.id.saveButton)
        safeButton.setOnClickListener {
            writeDataToFirebase(nameInput)
            Toast.makeText(this, "Safe!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.loadImageView)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageView.setImageURI(data?.data) // handle chosen image
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun writeDataToFirebase(nameInput: EditText, ) {
        database = Firebase.database
        myRef = database.getReference(name)
        myRef.setValue(nameInput.text.toString())

        Toast.makeText(this, "Сохранено", Toast.LENGTH_LONG).show()
    }
}