package com.example.shop_app.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shop_app.R
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page.view.*

class ArchivePageFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var adapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_archive_page, container, false)

//        adapter = ListAdapter()
//        val recyclerView = view.recyclerview
//        recyclerView.adapter = adapter
//        recyclerView.setHasFixedSize(true)
//        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
//        searchView = view.findViewById(R.id.search_view_archive)

        return view
    }

}