package com.example.haka

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MyCollection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener{
            val i = Intent(this, CreateCardActivity::class.java)
            startActivity(i)
        }

    }

}