package com.example.haka

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MyCollection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_collection)

        val nedvizhButton: Button = findViewById(R.id.nedvizh)
        nedvizhButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }


        val domButton: Button = findViewById(R.id.dom)
        domButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }


        val transportButton: Button = findViewById(R.id.transport)
        transportButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }

        val electroButton: Button = findViewById(R.id.electr)
        electroButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }


        val virtualButton: Button = findViewById(R.id.virtual)
        virtualButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }


        val hobbyButton: Button = findViewById(R.id.hobby)
        hobbyButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }


        val veschiButton: Button = findViewById(R.id.veschi)
        veschiButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }

        val animalsButton: Button = findViewById(R.id.animals)
        animalsButton.setOnClickListener{
            val i = Intent(this, SubCategoryActivity::class.java)
            startActivity(i)
        }

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener{
            val i = Intent(this, CreateCardActivity::class.java)
            startActivity(i)
        }

    }

}