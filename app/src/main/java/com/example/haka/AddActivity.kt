package com.example.haka

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide.with
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
import com.squareup.picasso.Picasso
import java.util.*


class AddActivity : AppCompatActivity() {
    val REQUEST_CODE = 100
    val path: String
        get() {
            val extras = intent.extras
            val value = extras!!.getString("data")
            Log.e("data", value.toString())
            val path =  convertCyrilic(value.toString()).toString()
            return path
            Log.e("datas", path)
        }

    lateinit var auth: FirebaseAuth
    lateinit var name: String
    lateinit var imageUrl: String

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

        val path =  convertCyrilic(value.toString()).toString()
        //Log.e("data", path.toString())
        //Log.e("data", extras.toString())

        val imageView = findViewById<ImageView>(R.id.loadImageView)
        imageView.setOnClickListener{
            openGalleryForImage()
        }

        auth = Firebase.auth
        name = auth.currentUser!!.displayName.toString()

        val safeButton = findViewById<Button>(R.id.saveButton)
        safeButton.setOnClickListener {
            writeDataToFirebase(nameInput)
            Toast.makeText(this, "????????????!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView = findViewById<ImageView>(R.id.loadImageView)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageView.setImageURI(data?.data) // handle chosen image
            //Log.e("data", data?.data.toString())
            if (data != null) {
                uploadImageToFirebase(data.data!!)
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
        Log.e("datas", intent.toString())
    }

    private fun writeDataToFirebase(nameInput: EditText) {
        database = Firebase.database
        myRef = database.getReference("UkulelesshopSrv").child(path)
        myRef.setValue(nameInput.text.toString())
        Log.e("datas", nameInput.text.toString())

        Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show()
    }


    private fun uploadImageToFirebase(fileUri: Uri) {
        if (fileUri != null) {
            val fileName = "$path.jpeg"

            val database = FirebaseDatabase.getInstance()
            val refStorage = FirebaseStorage.getInstance().reference.child("UkulelesshopSrv/$path/$path")
            Log.e("data", "UkulelesshopSrv/$path/$fileName")

            refStorage.putFile(fileUri)
                .addOnSuccessListener(
                    OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val imageUrl = it.toString()

//                            val i = Intent(this, AddActivity::class.java)
//                            i.putExtra("dataAdd", imageUrl);
//                            startActivity(i)

                            Log.e("datas", imageUrl)

                        }

                    })


                ?.addOnFailureListener(OnFailureListener { e ->
                    print(e.message)
                })
        }
    }

    fun convertCyrilic(message: String): String? {
        val abcCyr = charArrayOf(' ','??','??','??','??','??','??','??', '??','??','??','??','??','??','??','??','??','??','??','??','??','??','??','??', '??','??', '??','??','??','??','??','??', '??','??','??','??','??','??','??', '??','??','??','??','??','??','??','??','??','??','??','??','??','??','??','??', '??', '??','??', '??','??','??','??','??','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','/','-')
        val abcLat = arrayOf(" ","a","b","v","g","d","]","e","zh","z","y","i","j","k","l","q","m","n","w","o","p","r","s","t","'","u","f","h", "c","4", "x","{","A","B","V","G","D","}","E","Zh","Z","Y","I","J","K","L","Q","M","N","W","O","P","R","S","T","KJ","U","F","H", "C","4", "X","{", "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","/","-")
        val builder = StringBuilder()
        for (i in 0 until message.length) {
            for (x in abcCyr.indices) {
                if (message[i] == abcCyr[x]) {
                    builder.append(abcLat[x])
                }
            }
        }
        return builder.toString()
    }
}