package com.chaeniiz.rxjava_practice

import android.util.Log
import java.io.IOException
import java.net.InetAddress
import java.util.*

class CommonUtils {
        val GITHUB_ROOT = "https://raw.githubusercontent.com/yudong80/reactivejava/master/"

        val API_KEY = "5712cae3137a8f6bcbebe4fb35dfb434"

        val ERROR_CODE = "-500"

        var startTime: Long = 0

        private val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val isNetworkAvailable: Boolean
            get() {
                try {
                    return InetAddress.getByName("www.google.com").isReachable(1000)
                } catch (e: IOException) {
                    Log.e("d", "Network is not available")
                }

                return false
            }

        fun exampleStart() {
            startTime = System.currentTimeMillis()
        }

        fun exampleStart(obj: Any) {
            startTime = System.currentTimeMillis()
        }

        fun exampleComplete() {
            println("-----------------------")
        }

        fun sleep(millis: Int) {
            try {
                Thread.sleep(millis.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

        fun doSomething() {
            try {
                Thread.sleep(Random().nextInt(100).toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

        }

        fun numberToAlphabet(x: Long): String {
            return Character.toString(ALPHABET[x.toInt() % ALPHABET.length])
        }

        fun toInt(`val`: String): Int {
            return Integer.parseInt(`val`)
        }
}