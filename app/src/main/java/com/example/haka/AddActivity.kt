package com.example.haka

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*


class AddActivity : AppCompatActivity() {
    val REQUEST_CODE = 100
    lateinit var auth: FirebaseAuth
    lateinit var name: String

    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference

    private val reference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val nameInput = findViewById<EditText>(R.id.name)
        val describe = findViewById<TextView>(R.id.describe)

        val extras = intent.extras
        val value = extras!!.getString("data")
        Log.e("data", value.toString())
        describe.text = value.toString()

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
            Log.e("data", data?.data.toString())
            if (data != null) {
                uploadImageToFirebase(data.data!!)
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun writeDataToFirebase(nameInput: EditText) {
        database = Firebase.database
        myRef = database.getReference(name)
        myRef.setValue(nameInput.text.toString())

        Toast.makeText(this, "Сохранено", Toast.LENGTH_LONG).show()
    }


    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = UUID.randomUUID().toString() +".jpg"

            val database = FirebaseDatabase.getInstance()
            val refStorage = FirebaseStorage.getInstance().reference.child("$name/$fileName")

            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()
                        }
                    })

                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }
}