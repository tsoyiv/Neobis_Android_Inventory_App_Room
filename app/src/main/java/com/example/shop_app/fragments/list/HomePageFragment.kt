package com.example.shop_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import com.example.shop_app.R
import com.example.shop_app.RecyclerListener
import com.example.shop_app.databinding.ActivityMainBinding
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.*
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.view.*
import kotlinx.android.synthetic.main.fragment_home_page.view.*
import kotlin.collections.ArrayList

class HomePageFragment : Fragment(), RecyclerListener{

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

        val adapter = ListAdapter(this)
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        searchView = view.findViewById(R.id.search_view)

        searchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        searchDatabase(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        searchDatabase(newText)
                    }
                    return true
                }
            })
        }

        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)
        mShoeViewModel.readAllData.observe(viewLifecycleOwner, Observer { shoe ->
            adapter.setData(shoe)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_addPageFragment)
        }

        return view
    }
    private fun alertDialogArchive() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Архивировать Air Jordn из каталога?")
        builder.setPositiveButton("Да") { _,_ ->
        }
        builder.setNegativeButton("Нет") { _,_ ->
            builder.setCancelable(true)
        }
        builder.create().show()
    }
    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mShoeViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) { list ->
            list.let {
                val adapter = ListAdapter(this)
                adapter.setData(it)
            }
        }
    }

    override fun archiveProduct(shoe: Shoe) {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.bottom_dialog_for_main_fragment, null)

        dialogView.bottom_dialog_bt_archive.setOnClickListener {
            alertDialogArchive()
        }
        builder.setView(dialogView)
        builder.create().show()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//
//        val search = menu.findItem(R.id.search_view)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//    }
//    private fun init() {
//        val recyclerView = view?.recyclerview
//        recyclerView?.setHasFixedSize(true)
//        recyclerView?.layoutManager = GridLayoutManager(this, 2)
//    }
}