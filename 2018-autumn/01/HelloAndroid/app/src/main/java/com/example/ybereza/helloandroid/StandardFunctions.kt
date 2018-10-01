package com.example.ybereza.helloandroid

class MyClass {
    val myString = "My String"
    var myInt = 3

    fun call() {
        val substring = myString.let {
            it.substring(myInt)
        }
        println(substring)

        val sbs : String = myString.apply {
            substring(myInt)
        }
        println(sbs)
    }
}

fun withOrRun() {
    val lastVal : Int? = with(HashMap<String, Int>()) {
        put("Hello", 1)
        put("Android", 2)
    }


    val added : Boolean = ArrayList<Int>().run {
        add(42)
        add(100)
        add(200)
    }
}

fun withNullable(map : HashMap<String, Int>?) {
    with(map) {
        this?.put("Bye", 3)
        this?.put("Kotlin", 4)
    }

    map?.run {
        put("Key", 42)
        put("Value", 51)
    }
}