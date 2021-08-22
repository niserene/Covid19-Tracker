package com.example.covid_19tracker

import okhttp3.OkHttpClient
import okhttp3.Request

object Client{

    private val okHttpClient = OkHttpClient()

    val apiUrl:String = "https://data.covid19india.org/data.json"
    private val request = Request.Builder()
        .url(apiUrl)
        .build()

    val api = okHttpClient.newCall(request)

}