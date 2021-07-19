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
        val btn_save = findViewById<Button>(R.id.btn_save)

        // PHP + JSON 연결 데이터 가져오기
        val plug_name:String = intent.getStringExtra("name")
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
                Log.d("gson check","${gson}")
                val list = gson.fromJson(body, JsonObj::class.java)
                Log.d("list check","${list}")

                runOnUiThread {
                    reView.adapter = RecyclerAdapter(list, this@InformationActivity ,plug_name)
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request!")
                Log.d("json success","실패.")
            }
        })
        // 뒤로가기 버튼
        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        // 시간별 배터리 현황 그래프 버튼
        btn_time.setOnClickListener{
            val intent = Intent(this, TimeActivity::class.java)
            startActivity(intent)
            finish()
        }
        // 날짜별 감지 횟수 그래프 버튼
        btn_date.setOnClickListener {
            val intent = Intent(this, DateActivity::class.java)
            startActivity(intent)
            finish()
        }
        // 엑셀로 저장하기 버튼
        btn_save.setOnClickListener {

        }

    }


    }

 data class JsonObj(val result : ArrayList<RecycleData>)
 data class RecycleData (val idx : String, val fireplug_no: String, val is_parked: String,
                         val controller_battery: String, val light_battery : String,val speaker_battery:String, val regist_date : String)