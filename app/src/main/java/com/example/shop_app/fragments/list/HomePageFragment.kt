package com.example.shop_app.fragments.list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.request.SuccessResult
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageRequest
import com.example.shop_app.R
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import kotlinx.android.synthetic.main.fragment_home_page.view.*

class HomePageFragment : Fragment() {

    private lateinit var mShoeViewModel: ShoeViewModel
    private lateinit var itemList: ArrayList<Shoe>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        val adapter = ListAdapter()
//      val recyclerView = view.recyclerview
//      recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)


        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
        mShoeViewModel.readAllData.observe(viewLifecycleOwner, Observer { shoe ->
            adapter.setData(shoe)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_addPageFragment)
        }
        return view
    }

//    private fun init() {
//        val recyclerView = view?.recyclerview
//        recyclerView?.setHasFixedSize(true)
//        recyclerView?.layoutManager = GridLayoutManager(this, 2)
//    }
}