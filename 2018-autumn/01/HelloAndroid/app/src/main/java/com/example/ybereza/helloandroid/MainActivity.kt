package com.example.ybereza.helloandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // Это самое простое объявление функции
    fun getText() : String { return "Hello Android! Your Kotlin. "}

    // Однострочная функция
    fun singleLineFunction() = "Single Line!"

    // Функция принимающая один параметр
    fun oneParamFunction(who : String) : String {
        return "Hello $who! Your Kotlin"
    }

    // Функция возвращающая null
    fun nullReturn(who : String) : String? {
        return null;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val someText : String? = nullReturn("null")

        val text = findViewById<TextView>(R.id.text)
        text.text = oneParamFunction("Android")
    }
}