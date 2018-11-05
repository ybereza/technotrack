package ru.mail.technotrack.recyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import ru.mail.technotrack.recyclerview.*
import ru.mail.technotrack.recyclerview.adapters.ClickableAdapter
import ru.mail.technotrack.recyclerview.adapters.PageAdapter
import ru.mail.technotrack.recyclerview.adapters.SimpleListAdapter

class ListFragment : Fragment() {
    companion object {
        private const val STYLE_KEY = "LAYOUT_KEY"

        fun createListFragment(style : Int) : Fragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putInt(ListFragment.STYLE_KEY, style)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.list, container, false)
        val recycler : RecyclerView = layout.findViewById(R.id.list)

        recycler.setHasFixedSize(true)

        val style = arguments?.getInt(STYLE_KEY) ?: LIST

        when(style) {
            LIST -> createSimpleList(recycler)
            CLICKABLE_LIST -> createClickableList(recycler)
            PAGES -> createPageList(recycler)
            INNER_LIST -> createInnerList(recycler)
        }

        return layout
    }

    private fun createSimpleList(recycler : RecyclerView) {
        recycler.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        recycler.adapter = SimpleListAdapter()
    }

    private fun createClickableList(recycler : RecyclerView) {
        val layoutManager = GridLayoutManager(
            requireContext(),
            2,
            RecyclerView.VERTICAL,
            false
        )
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 0) 2 else 1
            }
        }
        recycler.layoutManager = layoutManager
        recycler.adapter = ClickableAdapter()
    }

    private fun createPageList(recycler: RecyclerView) {
        recycler.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )

        PagerSnapHelper().attachToRecyclerView(recycler)
        recycler.adapter = PageAdapter()
    }

    private fun createInnerList(recycler: RecyclerView) {

    }
}