package ru.mail.technotrack.mainui.data

//Use data class to store text from fragment inside Application class
data class ResultFragmentState (var text : String)

//Use TextHolder Singleton to store text from fragment
object TextHolder {
    var text : String = ""
}