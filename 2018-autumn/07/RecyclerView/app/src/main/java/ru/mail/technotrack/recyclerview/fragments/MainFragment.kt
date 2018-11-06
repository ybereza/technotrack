package ru.mail.technotrack.recyclerview.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.mail.technotrack.recyclerview.*
import ru.mail.technotrack.recyclerview.adapters.ButtonsListAdapter
import java.lang.IllegalStateException

class MainFragment : Fragment() {

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_main, container, false)
        val buttons : RecyclerView = layout.findViewById(R.id.buttons)

        buttons.layoutManager = LinearLayoutManager(
            inflater.context,
            RecyclerView.VERTICAL,
            false
        )
        buttons.adapter = ButtonsListAdapter(createButtons(), ::onButtonClick)

        return layout
    }

    private fun createButtons() : Array<String> {
        return arrayOf(
            "Simple List",
            "Clickable List",
            "Pages",
            "Create notification"
        )
    }

    private fun onButtonClick(position : Int) = when(position) {
        0 -> router.navigateTo { ListFragment.createListFragment(LIST) }
        1 -> router.navigateTo { ListFragment.createListFragment(CLICKABLE_LIST) }
        2 -> router.navigateTo { ListFragment.createListFragment(PAGES) }
        3 -> createNotification()
        else -> throw IllegalStateException()
    }

    private fun createNotification() {
        createSimpleNotification(requireContext())
    }
}
