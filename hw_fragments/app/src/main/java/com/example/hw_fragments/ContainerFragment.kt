package com.example.hw_fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_container.*
import java.lang.StringBuilder

class ContainerFragment : Fragment() {

    companion object {
        const val KEY = "fragment_key"
        fun newInstance(key: String): Fragment {
            val fragment = ContainerFragment()
            val argument = Bundle()
            argument.putString(KEY, key)
            fragment.arguments = argument
            return fragment
        }
    }

    private var cnt = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            cnt = childFragmentManager.backStackEntryCount
        }
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val key = it.getString(KEY)
            text_title.text = key

            button_open_child_fragment.setOnClickListener {
                val childKey = "$key${cnt + 1}"
                val childText = generateText(cnt + 1)
                childFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, ChildFragment.newInstance(childText), childKey)
                    .addToBackStack(childKey)
                    .commit()
            }
        }

        childFragmentManager.addOnBackStackChangedListener {
            cnt = childFragmentManager.backStackEntryCount
        }
    }

    private fun generateText(cnt: Int): String {
        val res = StringBuilder()
        for (i in 1 until cnt)
            res.append(i).append(" => ")
        res.append(cnt)
        return res.toString()
    }

}
