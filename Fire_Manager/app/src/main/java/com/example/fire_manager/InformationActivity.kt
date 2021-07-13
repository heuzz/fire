 package com.example.fire_manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class InformationActivity : AppCompatActivity() {

    private val firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    val list =  ArrayList<RecycleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val name = intent.getStringExtra("name")
        val main: TextView = findViewById(R.id.bet_Arduino)
        val led: TextView = findViewById(R.id.bet_Light)
        val speaker: TextView = findViewById(R.id.bet_Speaker)
        val btn_back: ImageButton = findViewById(R.id.btn_back)
        val btn_time = findViewById<Button>(R.id.btn_time)
        val btn_date = findViewById<Button>(R.id.btn_date)
        val mRecyclerView = findViewById<RecyclerView>(R.id.reView)
        val adapter = RecyclerAdapter(list,this@InformationActivity)

        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_time.setOnClickListener{
            val intent = Intent(this, TimeActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_date.setOnClickListener {
            val intent = Intent(this, DateActivity::class.java)
            startActivity(intent)
            finish()
        }



        databaseReference.child("fire1").child("battery").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot != null) {
                        Log.d("MainActivity", "Single ValueEventListener : " + dataSnapshot.getValue())
                        main.text = "${dataSnapshot.child("main").getValue()}" +"%"
                        led.text = "${dataSnapshot.child("led").getValue()}" + "%"
                        speaker.text = "${dataSnapshot.child("speaker").getValue()}" + "%"

                }else{
                    Log.d("MainActivity", "NO Data" + dataSnapshot.getValue())
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        databaseReference.child("fire1").child("record").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot != null) {
                    dataSnapshot.children.forEach { i ->
                        Log.d("MainActivity", "Single ValueEventListener : " + i.getValue())
                        list.add(RecycleData("${i.key.toString()}","${i.child("date").getValue().toString()}",
                            "${i.child("time").getValue().toString()}"))
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Datasnapshot is null", Toast.LENGTH_SHORT).show()
                }
                val adapter = RecyclerAdapter(list,this@InformationActivity)
                mRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        mRecyclerView.adapter = adapter
        mRecyclerView.setHasFixedSize(true)

    }
}