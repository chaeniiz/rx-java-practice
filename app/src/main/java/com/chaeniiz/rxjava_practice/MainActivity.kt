package com.chaeniiz.rxjava_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private val URL = "http://api.openweathermap.org/data/2.5/weather?q=London&APPID=e4df7c76d3271f1aa12284310650eb86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        run()
    }

    fun run() {
        CommonUtils().exampleStart()

        /*
    Observable<String> source = Observable.just(URL)
            .map(OkHttpHelper::getWithLog)
            .subscribeOn(Schedulers.io())
            .share()
            .observeOn(Schedulers.io());
    */

        /*
    Maybe<String> source = Maybe.just(URL)
            .map(OkHttpHelper::getWithLog)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io());

            0개 또는 1개를 발행하는 것이라서 Maybe도 잘 동작한다.
            근데 그럼 모든 Single은 전부 Maybe로 작성해도 되는 걸까..?!?!?!?!
    */

        val source = Single.just(URL)
                .map(OkHttpHelper()::getWithLog)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())

        val changedSource = Completable.fromSingle<String>(source)

        source.map(this::parseTemperature).subscribe()
        source.map(this::parseCityName).subscribe()
        source.map(this::parseCountry).subscribe()

        CommonUtils().sleep(1000)
    }

    fun parseTemperature(json: String): String {
        return parseApi(json, "\"temp\":[0-9]*.[0-9]*")
    }

    fun parseCityName(json: String): String {
        return parseApi(json, "\"name\":\"[a-zA-Z]*\"")
    }

    fun parseCountry(json: String): String {
        return parseApi(json, "\"country\":\"[a-zA-Z]*\"")
    }

    fun parseApi(json: String, regex: String): String {
        val pattern = Pattern.compile(regex)
        val match = pattern.matcher(json)
        return if (match.find()) {
            match.group()
        } else "N/A"
    }



}
