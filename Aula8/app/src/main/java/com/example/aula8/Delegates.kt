package com.example.aula8

import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KProperty

class SetContentView<out T: ViewDataBinding>(@LayoutRes private val layoutRes: Int) {

    private var value: T? = null

    operator fun getValue(thisRef: Activity, property: KProperty<*>): T {
        this.value = value ?: DataBindingUtil.setContentView(thisRef, layoutRes)
        return value!!
    }
}

fun <R: BaseObservable, T: Any> bindable(
    value: T,
    bindingEntry: Int
) = BindableDelegate<R, T>(value, bindingEntry)

class BindableDelegate<R: BaseObservable, T: Any>(
    private var value: T,
    private val bindingEntry: Int
) {
    operator fun getValue(thisRef: R, property: KProperty<*>) = value

    operator fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        if (this.value != value) {
            this.value = value
            thisRef.notifyPropertyChanged(bindingEntry)
        }
    }
}