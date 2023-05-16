package com.example.shop_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shop_app.R
import com.example.shop_app.RecyclerListener
import com.example.shop_app.databinding.ActivityMainBinding
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import kotlinx.android.synthetic.main.fragment_archive_page.view.*
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_home_page.view.*
import androidx.lifecycle.Observer
import com.example.shop_app.data.ShoeApplication
import com.example.shop_app.databinding.FragmentArchivePageBinding
import com.example.shop_app.viewmodel.ArchiveViewModel
import com.example.shop_app.viewmodel.ArchiveViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.view.*
import kotlinx.android.synthetic.main.bottom_for_archive.*
import kotlinx.android.synthetic.main.bottom_for_archive.view.*


class ArchivePageFragment : Fragment(), RecyclerListener {

    private lateinit var mShoeViewModel: ShoeViewModel
    private lateinit var searchView: SearchView
    private lateinit var binding: FragmentArchivePageBinding
    private val mViewModel by viewModels<ArchiveViewModel> {
        ArchiveViewModelFactory((requireActivity().application as ShoeApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_archive_page, container, false)

        val adapter = ListAdapter(this)
        val recyclerView = view.recyclerview_archive
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)
        searchView = view.findViewById(R.id.search_view_archive)

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
        mViewModel.archiveShoes.observe(viewLifecycleOwner, Observer { shoe ->
            adapter.setData(shoe)
        })
        return view
    }

    private fun searchDatabase(query: String) {
        val searchQuery = "%$query%"

        mViewModel.getSearchArchiveProduct(searchQuery).observe(viewLifecycleOwner) { list ->
            list.let {
                val adapter = ListAdapter(this)
                adapter.setData(it)
            }
        }

    }

    override fun archiveProduct(shoe: Shoe) {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.bottom_for_archive, null)

        bottom_archive_delete_archive.setOnClickListener {
            deleteFunction(shoe)
        }

        bottom_archive_in_archive.setOnClickListener {
            alertDialogIn(shoe)
        }
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
    }

    private fun deleteFunction(shoe: Shoe) {
        TODO("Not yet implemented")
    }

    private fun alertDialogIn(shoe: Shoe) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Востановить ${shoe.name} из архива?")
            setPositiveButton("Да") { list, _ ->
                mViewModel.unArchiveData(shoe)
                list.dismiss()
                Toast.makeText(
                    requireContext(),
                    "Разорхивировано",
                    Toast.LENGTH_SHORT
                ).show()
            }
            setNegativeButton("Нет") { list, _ ->
                list.dismiss()
            }
            create()
            show()
        }
    }
}