package com.chaeniiz.rxjava_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ObserveOnSubscribeOn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observe_on_subscribe_on)

        observeOnSubscribeOn()
    }

    fun observeOnSubscribeOn() {
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

        /*
        1. 스케줄러는 RxJava 코드를 어느 스레드에서 실행할지 지정할 수 있다.
        2. subscribeOn() 함수와 observeOn() 함수를 모두 지정하면
            Observable에서 데이터 흐름이 발생하는 스레드와 처리된 결과를 구독자에게 발행하는
            스레드를 분리할 수 있다.
        3. subscribeOn() 함수만 호출하면 Observable의 모든 흐름이 동일한 스레드에서 실행된다.
        4. 스케줄러를 별도로 지정하지 않으면 현재(main) 스레드에서 동작을 실행한다.
        */

        val args = arrayOf("1", "3", "5")

        Observable.fromArray(*args)
                .doOnNext { data -> Log.v("#", "original data : $data") }
                .map { data -> "<<$data>>" }
                .subscribeOn(Schedulers.newThread())
                .subscribe { Log.i("current scheduler : ", Thread.currentThread().toString()) }

        Thread.sleep(500)

        Observable.fromArray(*args)
                .doOnNext { data -> Log.v("#", "original data : $data") }
                .map { data -> "**$data**" }
                .subscribeOn(Schedulers.io())
                .subscribe { Log.i("current scheduler : ", Thread.currentThread().toString()) }

        val singleSource: Single<String> = Single.just("Hello Single")
        singleSource.subscribe {
            e -> Log.e("# Single #", singleSource.toString())
        }

        val maybeSource: Maybe<ArrayList<String>> = Maybe.just(arrayListOf("Hello Single"))
        maybeSource.subscribe {
            e -> Log.e("# Maybe #", maybeSource.toString())
        }

        /*
        val completableSource: Completable = Completable.just(arrayListOf("Hello Single"))
        completableSource.subscribe {
            e -> Log.e("# Maybe #", maybeSource.toString())
        }*/

        // completable은 발행하는 것이 없기 때문에 argument도 없고, just로 발행할 수 있는 함수도 없음
    }
}
