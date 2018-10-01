package com.example.ybereza.helloandroid

// Это самое простое объявление функции
fun nothing() {}

// Однострочная функция
fun singleLineFunction() = "Single Line!"

// Однострочная функция
fun sum(a : Int, b : Int) = a + b

// Функция принимающая один параметр
fun oneParamFunction(who : String) : String {
    return "Hello $who! Your Kotlin"
}

// Функция имеющая параметр по умолчанию и возвращающая значение типа String
fun getGreetings(greetings : String = "Hello Android! Your Kotlin. ") : String {
    return greetings
}

// Функция возвращающая null
fun nullReturn(who : String) : String? {
    return null;
}

//Функция с именованными аргументами
fun joinGreeting(greeting : String = "Hello", who : String = "Android", from : String = "Kotlin") : String {
    val builder = StringBuilder()
            .append(greeting)
            .append(" ")
            .append(who)
            .append("!")
            .append(" From ")
            .append(from)
    return builder.toString()
}

fun greetingFromTechnotrack() = joinGreeting(from = "Technotrack", who = "students")

fun localFunctions(name : String) {

    fun validateOrThrow() {
        if (name.isEmpty() || name.equals("Ivanov")) {
            throw IllegalStateException("Incorrect name")
        }
    }
    validateOrThrow()
    //
    //do next thing
    //
    validateOrThrow()
}