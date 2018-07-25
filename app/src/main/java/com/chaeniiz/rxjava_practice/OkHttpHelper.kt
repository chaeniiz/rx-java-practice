package com.chaeniiz.rxjava_practice

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class OkHttpHelper {
    private val client = OkHttpClient()
    var ERROR = "ERROR"

    @Throws(IOException::class)
    operator fun get(url: String): String {
        val request = Request.Builder()
                .url(url)
                .build()
        try {
            val res = client.newCall(request).execute()
            return res.body()!!.string()
        } catch (e: IOException) {
            Log.e("#", e.message)
            throw e
        }

    }

    @Throws(IOException::class)
    fun getT(url: String): String {
        val request = Request.Builder()
                .url(url)
                .build()
        try {
            val res = client.newCall(request).execute()
            return res.body()!!.string()
        } catch (e: IOException) {
            Log.e("#", e.message)
            throw e
        }

    }

    @Throws(IOException::class)
    fun getWithLog(url: String): String {
        Log.d("#", "OkHttp call URL = $url")
        Log.e("current Thread: ", Thread.currentThread().toString())
        return get(url)
    }
}