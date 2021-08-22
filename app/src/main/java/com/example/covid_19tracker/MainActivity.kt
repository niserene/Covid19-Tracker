package com.example.covid_19tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
//import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var stateItemAdapter:StateItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        
        fetchResults()

    }

    private fun fetchResults() {

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO){
                Client.api.execute()
            }
            if(response.isSuccessful){
                val data:Response = Gson().fromJson(response.body?.string(), Response::class.java)
                launch(Dispatchers.Main){
                    bindCombinedData(data.statewise[0])
                    bindStateWiseData(data.statewise.subList(1, data.statewise.size))
                }
            }
        }

    }

    private fun bindStateWiseData(data: List<statewiseItem>) {
        stateItemAdapter = StateItemAdapter(data)
        dataRv.layoutManager = LinearLayoutManager(this)
        dataRv.adapter = stateItemAdapter
    }

    private fun bindCombinedData(data: statewiseItem) {

        val lastUpdatedTime = data.lastupdatedtime
        val simpleDateFormat =  SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastUpdatedTv.text = "Last updated \n ${getTimeAgo(simpleDateFormat.parse(lastUpdatedTime))}"

        confirm_tv.text = data.confirmed
        recover_tv.text = data.recovered
        death_tv.text = data.deaths
        active_tv.text = data.active
    }

    fun getTimeAgo(past:Date):String{
        val now = Date()
        val seconds:Long = TimeUnit.MILLISECONDS.toSeconds(now.time-past.time)
        val minutes:Long = TimeUnit.MILLISECONDS.toMinutes(now.time-past.time)
        val hours:Long = TimeUnit.MILLISECONDS.toHours(now.time-past.time)

        return when{
            seconds < 60 ->{
                "Few seconds ago"
            }
            minutes < 60 ->{
                "$minutes min ago"
            }
            hours < 24 ->{
                "$hours hrs ago"
            }
            else ->{
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }
}