package com.example.haka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class CreateCardActivity : AppCompatActivity() {
    lateinit var text: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

        val text = findViewById<EditText>(R.id.nameProd)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            val i = Intent(this, AddActivity::class.java)
            i.putExtra("data", text.text.toString());
            startActivity(i)
        }
    }
}