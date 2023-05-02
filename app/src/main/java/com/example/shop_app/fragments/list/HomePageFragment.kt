package com.example.shop_app.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop_app.R
import com.example.shop_app.data.ShoeViewModel
import kotlinx.android.synthetic.main.fragment_home_page.view.*

class HomePageFragment : Fragment() {

    private lateinit var mShoeViewModel: ShoeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home_page, container, false)

        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
        mShoeViewModel.readAllData.observe(viewLifecycleOwner, Observer { shoe ->
            adapter.setData(shoe)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_homePageFragment_to_addPageFragment)
        }
        return view
    }
}