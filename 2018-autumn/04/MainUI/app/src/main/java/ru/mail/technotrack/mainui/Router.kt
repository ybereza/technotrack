package ru.mail.technotrack.mainui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

class Router(activity : FragmentActivity, container: Int) {
    private val weakActivity = WeakReference(activity)
    private val fragmentContainer = container

    fun navigateTo(addToBack : Boolean = true, fragmentFactory: () -> Fragment) {
        val activity = weakActivity.get()

        activity?.run {
            val fragment = fragmentFactory()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(fragmentContainer, fragment)
            if (addToBack) transaction.addToBackStack(fragment::class.java.simpleName)
            transaction.commit()
        }
    }

    fun navigateBack() : Boolean {
        val activity = weakActivity.get()

        activity?.run {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                return true
            }
        }

        return false
    }
}