 package com.example.fire_manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_information.*
import okhttp3.*
import java.io.IOException
import java.net.URL

 class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)


        val btn_back: ImageButton = findViewById(R.id.btn_back)
        val btn_time = findViewById<Button>(R.id.btn_time)
        val btn_date = findViewById<Button>(R.id.btn_date)


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

        fetchJson()


    }
    fun fetchJson() {
        val url = URL("http://3.34.252.103/json.php")
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                println("Success to execute request! : $body")
                Log.d("json success","제대로 가져왔습니다.")
                //Gson으로 파싱
                val gson = GsonBuilder().create()
                val list = gson.fromJson(body, JsonObj::class.java)
                Log.d("list check","${list}")

                //println(list.result[0].name)
                //여기서 나온 결과를 어답터로 연결
                runOnUiThread {
                    reView.adapter = RecyclerAdapter(list, this@InformationActivity)
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request!")
                Log.d("json success","실패.")
            }
        })

        }

}
 data class JsonObj(val result : List<RecycleData>)
 data class RecycleData (val count : String, val fireplug_no: String, val is_parked: String,
                         val controller: String, val light : String,val speaker:String, val date : String)