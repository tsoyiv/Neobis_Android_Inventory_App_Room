package com.example.shop_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.shop_app.R
import com.example.shop_app.RecyclerListener
import com.example.shop_app.data.ShoeApplication
import com.example.shop_app.databinding.ActivityMainBinding
import com.example.shop_app.databinding.FragmentAddPageBinding
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import com.example.shop_app.viewmodel.ShoeViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.*
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.view.*
import kotlinx.android.synthetic.main.fragment_home_page.view.*
import kotlin.collections.ArrayList

class HomePageFragment : Fragment(), RecyclerListener {

    private val mViewModel by viewModels<ShoeViewModel> {
        ShoeViewModelFactory((requireActivity().application as ShoeApplication).repository)
    }
    private lateinit var searchView: SearchView
    private lateinit var binding: FragmentAddPageBinding

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

        mViewModel.getAllData(false).observe(viewLifecycleOwner, Observer { shoe ->
            adapter.setData(shoe)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homePageFragment_to_addPageFragment)
        }

        return view
    }

    override fun archiveProduct(shoe: Shoe) {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.bottom_dialog_for_main_fragment, null)

        dialogView.bottom_dialog_bt_archive.setOnClickListener {
            logicForAlertDialog(shoe)
        }
        builder.setView(dialogView)
        builder.create().show()
    }

    private fun logicForAlertDialog(shoe: Shoe) {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Архивировать ${shoe.name} из каталога")
            setPositiveButton("Да") { _, _ ->
                mViewModel.updateData(
                    Shoe(
                        shoe.id,
                        shoe.name,
                        shoe.price,
                        shoe.distributor,
                        shoe.amount,
                        shoe.shoeImage,
                        true
                    )
                )
                Toast.makeText(requireContext(), "Заархивировано", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Нет") { p, _ ->
                p.dismiss()
            }
            create()
            show()
        }
    }

    //    private fun alertDialogArchive() {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Архивировать Air Jordn из каталога?")
//        builder.setPositiveButton("Да") { _,_ ->
//        }
//        builder.setNegativeButton("Нет") { _,_ ->
//            builder.setCancelable(true)
//        }
//        builder.create().show()
//    }
    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mViewModel.getAllSearchProduct(searchQuery).observe(viewLifecycleOwner) { list ->
            list.let {
                val adapter = ListAdapter(this)
                adapter.setData(it)
            }
        }
    }
}