package com.chaeniiz.rxjava_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable = Observable.create<Int> { subscriber ->
            for (i in arrayOf(1, 2, 3, 4, 5)) {
                Log.e("observable 1 #", Thread.currentThread().name + " : onNext " + i)
                subscriber.onNext(i)
            }
        }

        observable.subscribe { e -> Log.e("observable 1 #", Thread.currentThread().name + " : onErrorHandler") }

        val observable2 = Observable.create<Int> { subscriber ->
            for (i in arrayOf(1, 2, 3, 4, 5)) {
                Log.e("observable 2 #", Thread.currentThread().name + " : onNext " + i)
                subscriber.onNext(i)
            }
        }

        observable2.subscribeOn(Schedulers.computation())
                .subscribe { e -> Log.e("observable 2 #", Thread.currentThread().name + " : onErrorHandler") }

        val observable3 = Observable.create<Int> { subscriber ->
            for (i in arrayOf(1, 2, 3, 4, 5)) {
                Log.e("observable 3 #", Thread.currentThread().name + " : onNext " + i)
                subscriber.onNext(i)
            }
        }

        observable3
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { e -> Log.e("observable 3 #", Thread.currentThread().name + " : onErrorHandler") }

        val observable4 = Observable.create<Int> { subscriber ->
            for (i in arrayOf(1, 2, 3, 4, 5)) {
                Log.e("observable 4 #", Thread.currentThread().name + " : onNext " + i)
                subscriber.onNext(i)
            }
        }

        observable4
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .concatMap<Any> { integer ->
                    val newinteger = integer * 10
                    Log.e("observable 4 #", Thread.currentThread().name + " : concatMap value :" + newinteger)
                    Observable.just(newinteger)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { e -> Log.e("observable 4 #", Thread.currentThread().name + " : onErrorHandler") }
    }
}
