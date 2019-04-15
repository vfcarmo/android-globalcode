package com.example.aula12

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.Flowables
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNameObservable = RxTextView.textChanges(etName)
            .skipInitialValue()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.LATEST)

        val etYearObservable = RxTextView.textChanges(etYear)
            .skipInitialValue()
            .debounce(500L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable(BackpressureStrategy.LATEST)

        this.disposable = Flowables.combineLatest(
            etNameObservable,
            etYearObservable
        ) { newName, newYear ->
            val nameValid = newName.length >= 3

            if (!nameValid) {
                etName.error = "Invalid name"
            }

            val yearValid = newYear.length == 4
            if (!yearValid) {
                etYear.error = "Invalid year"
            }
            (nameValid && yearValid)
        }.subscribe { formValid ->
            btAdd.setBackgroundColor(
                ContextCompat.getColor(applicationContext,
                    if (formValid) R.color.colorPrimary else android.R.color.darker_gray)
            )
            btAdd.isEnabled = formValid
        }

        btAdd.setOnClickListener {
            Toast.makeText(this, "Clicou!", Toast.LENGTH_LONG).show()
        }

/*
        val observable = Observable.just(1, 2, 3, 4)

        val observer = object : Observer<Int> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                println("onSubscribe $d")
            }

            override fun onNext(t: Int) {
                println("onNext $t")
            }

            override fun onError(e: Throwable) {
                println("onError $e")
            }

        }

        observable.subscribe(observer)

        val consumer = object : Consumer<Int> {
            override fun accept(t: Int?) {
                println("onNext $t")
            }

        }

        val disposable = observable.subscribe(consumer)
        disposable.dispose()
*/

    }

    override fun onDestroy() {
        this.disposable.dispose()

        super.onDestroy()
    }
}


