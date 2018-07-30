package com.chaeniiz.rxjava_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

class FromCallable : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_from_callable2)

        fromCallableTest()
    }

    fun fromCallableTest() {
        val singleCallable: Single<String> = Single.fromCallable(
                object : Callable<String> {
                    override fun call(): String {
                        Log.e("# Single #", Thread.currentThread().toString())
                        Thread.sleep(1000)
                        return "Hello callable"
                    }
                }
        )
        singleCallable.subscribeOn(Schedulers.io())
                .subscribe { e ->
                    Log.e("# Single #", Thread.currentThread().toString())
                }

        val singleSource: Single<String> = Single.just("Hello Single")
        singleSource.observeOn(Schedulers.io()).subscribe {
            e -> Log.e("# Single #######", Thread.currentThread().toString())
        }
    }

    /*
    fromCallable vs subscribeOn...... 등등 차이점은

    스레드가 생성 시점에 바뀌는지, 아닌지의 차이.

    callable은 Callable 객체 생성 시점이 아닌, subscribe 시점에 스레드가 할당됨.

    Single.just는 생성 시점에 스레드 할당.

    그래서 subscribe 시점에 스레드를 할당하고 싶을 때 callable을 사용함.
     */
}
