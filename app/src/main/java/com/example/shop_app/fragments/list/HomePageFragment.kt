package com.example.shop_app.fragments.list

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.request.SuccessResult
import com.android.volley.toolbox.ImageLoader
import androidx.appcompat.widget.SearchView
import com.android.volley.toolbox.ImageRequest
import com.example.shop_app.R
import com.example.shop_app.databinding.ActivityMainBinding
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.*
import kotlinx.android.synthetic.main.fragment_home_page.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomePageFragment : Fragment() {

    private lateinit var mShoeViewModel: ShoeViewModel
    private lateinit var itemList: ArrayList<Shoe>
    private lateinit var searchView: SearchView
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionButtonItem: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        searchView = view.findViewById(R.id.search_view)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if (query != null) {
//                    searchDatabase(query)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(query: String): Boolean {
//                if (query != null) {
//                    searchDatabase(query)
//                }
//                return true
//            }
//        })


        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
        mShoeViewModel.readAllData.observe(viewLifecycleOwner, Observer { shoe ->
            adapter.setData(shoe)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_addPageFragment)
        }

        return view
    }

    private fun filterList(query: String?, fromDatabase: Boolean = false) {
        if (query != null) {
            val filteredList = ArrayList<Shoe>()
            val adapter = ListAdapter()
            if (!fromDatabase) {
                for (i in kotlin.collections.ArrayList<Shoe>()) {
                    if (i.name.toLowerCase(Locale.ROOT).contains(query)) {
                        filteredList.add(i)
                    }
                }
            } else {
                val searchQuery = "%$query%"
                mShoeViewModel.searchDatabase(searchQuery).observe(this) { list ->
                    list?.let {
                        filteredList.addAll(it)
                        adapter.setData(it)
                    }
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
            } else {
                val adapter = ListAdapter()
                adapter.setList(filteredList)
            }
        }
    }


//    private fun filterList(query: String?) {
//        if (query != null) {
//            val filteredList = ArrayList<Shoe>()
//            for(i in kotlin.collections.ArrayList<Shoe>()) {
//                if (i.name.toLowerCase(Locale.ROOT).contains(query)) {
//                    filteredList.add(i)
//                }
//            }
//            if (filteredList.isEmpty()) {
//                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
//            } else {
//                val adapter = ListAdapter()
//                adapter.setList(filteredList)
//            }
//        }
//    }
//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//
//        val search = menu.findItem(R.id.search_view)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//    }
//    private fun searchDatabase(query: String) {
//        val searchQuery = "%$query%"
//
//        mShoeViewModel.searchDatabase(searchQuery).observe(this) { list ->
//            list.let {
//                val adapter = ListAdapter()
//                adapter.setData(it)
//            }
//        }
//    }
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        if (query != null) {
//            searchDatabase(query)
//        }
//        return true
//    }
//
//    override fun onQueryTextChange(query: String): Boolean {
//        if (query != null) {
//            searchDatabase(query)
//        }
//        return true
//    }

//    private fun init() {
//        val recyclerView = view?.recyclerview
//        recyclerView?.setHasFixedSize(true)
//        recyclerView?.layoutManager = GridLayoutManager(this, 2)
//    }
}