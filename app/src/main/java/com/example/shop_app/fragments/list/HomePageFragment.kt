package com.example.shop_app.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.shop_app.R
import kotlinx.android.synthetic.main.fragment_home_page.view.*

class HomePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home_page, container, false)

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_homePageFragment_to_addPageFragment)
        }

        return view
    }

}