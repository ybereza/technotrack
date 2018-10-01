package com.example.ybereza.helloandroid

// Функция принимает два параметра и лямбду (фрагмент кода),
// осуществляющий операцию преобразования
fun doOperation(x : Int, y : Int, transform : (Int, Int) -> Int) {
    val result = transform(x, y)
}

fun callOfLambda() {
    //Лямбда, которая на входе получает два числа возвращает их сумму
    val sum = { x: Int, y : Int -> x + y }

    //Лямбда, которая на входе получает два числа и печатает их значаение
    val printer = { x : Int, y : Int ->
        println(x)
        println(y)
        0
    }

    doOperation(1, 2, sum)
    doOperation(1, 2, printer)
}

data class Person(val name : String, val age : Int)

fun findOldest() {
    val persons = arrayOf(Person("Bob", 29),
                          Person("Alice", 20))

    val oldest = persons.maxBy({ p : Person -> p.age })

    println(oldest)
}

fun simpleCallOfLambda() {
    doOperation(1, 2) { x, y ->
        x + y
    }
}

fun threadCall() {
    val thread = Thread {
        println("Hello from background thread")
    }

    val runnable = Runnable {
        println("Hello from background thread")
    }

    val secondThread = Thread(runnable)

    thread.start()
    secondThread.start()
}