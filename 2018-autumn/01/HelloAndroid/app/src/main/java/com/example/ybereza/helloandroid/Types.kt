package com.example.ybereza.helloandroid

fun declaration() {
    val a : Int = 0

    val b = 0

    var c : String = "Hello"

    val d = a == b // OK, same type

    c = "World!"

    //b = 10 // Error
}

fun conversion() {

    val a : Int = 0
    val b : Long = 0

    //val compairError =  a == b // Error
    val compair = a.toLong() == b // OK
}

fun arrays() {
    val stringArrayOfFive = Array(5, { i -> i.toString() })

    val IntArrayOfThree = arrayOf(1, 2, 3)

    IntArrayOfThree[0] = IntArrayOfThree[1] + IntArrayOfThree[2]
}

fun main(args : Array<String>) {
    println(args.size)
}