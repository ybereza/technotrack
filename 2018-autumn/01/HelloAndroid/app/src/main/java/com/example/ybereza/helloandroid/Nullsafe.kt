package com.example.ybereza.helloandroid

fun nullSafeError() : String {
    //return null // Error
    return ""
}

fun nullSafe() : String {
    return ""
}

fun nullUnsafe() : String? {
    return null // Ok
}

fun sample() {
    val name : String = nullSafe() // OK

    //val surname : String = nullUnsafe() // Error

    val lastname : String? = nullUnsafe() // OK

    //val len = lastname.length // Error

    val checkedLen = lastname?.length // OK

    val dl = if (lastname != null) lastname.length else 42 // OK

    val defaultLen = lastname?.length ?: 42 // OK
}

