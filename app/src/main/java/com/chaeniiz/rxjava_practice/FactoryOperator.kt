package com.chaeniiz.rxjava_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class FactoryOperator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factory_operator)

        testJust()
        testCreate()
    }

    fun testJust() {
        val source = Observable.just(1, 2, 3, 4, 5, 6)
                .map{ Log.e("items: ", it.toString()) }
                //.subscribe{ Log.e("items: ", it.toString()) }
    }

    fun testCreate() {
        val source = Observable.create { emitter: ObservableEmitter<Int> ->
            emitter.onNext(100)
            emitter.onNext(200)
            emitter.onNext(300)
            emitter.onNext(400)
            emitter.onComplete()
        }
        source.subscribe{ Log.e("values: ", it.toString()) }
    }

}
