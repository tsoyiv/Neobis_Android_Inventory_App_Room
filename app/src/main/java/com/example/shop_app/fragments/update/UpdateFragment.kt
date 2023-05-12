package com.example.shop_app.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shop_app.R
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import com.google.android.material.progressindicator.BaseProgressIndicator.ShowAnimationBehavior
import kotlinx.android.synthetic.main.fragment_add_page.*
import kotlinx.android.synthetic.main.fragment_add_page.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.cancel_text_btn

class UpdateFragment : Fragment() {

    private val args: UpdateFragmentArgs by navArgs()
    private lateinit var mShoeViewModel: ShoeViewModel
    private lateinit var editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        args.currentShoe?.let { shoe ->
            view.item_updateName_text.setText(shoe.name)
            view.item_updatePrice_text.setText(shoe.price)
            view.update_distri_text.setText(shoe.distributor)
            view.item_updateAmount_text.setText(shoe.amount)

            view.update_button.setOnClickListener {
                updateItem()
            }
        }

//        view.update_button.setOnClickListener {
//            updateItem()
//        }
//
//        view.arrow_update_fragment_back.setOnClickListener {
//            findNavController().navigate(R.id.action_updateFragment_to_homePageFragment)
//        }
//        view.cancel_text_btn.setOnClickListener {
//            findNavController().navigate(R.id.action_updateFragment_to_homePageFragment)
//        }
//
//        view.update_remove_btn.setOnClickListener {
//            deleteShoe()
//        }

        return view
    }

    private fun deleteShoe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _,_ ->
            mShoeViewModel.deleteAllShoe(args.currentShoe)
            Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No") { _,_ ->
            builder.setTitle("Delete ${args.currentShoe?.name}?")
            builder.setMessage("Are you sure ${args.currentShoe?.name}?")
            builder.create().show()
        }
    }

    private fun updateItem() {
        val name = item_updateName_text.text.toString()
        val price = item_updatePrice_text.text.toString()
        val distributor = item_updateDistributor_text.text.toString()
        val amount = item_updateAmount_text.text.toString()

        if (inputCheck(name, price, distributor, amount)) {
            val updatedShoe = args.currentShoe?.let { Shoe(it.id, name, price, distributor, amount) }
            updatedShoe?.let { mShoeViewModel.updateShoe(it) }
            findNavController().navigate(R.id.action_updateFragment_to_homePageFragment)
            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Please, fill out", Toast.LENGTH_LONG).show()
        }
    }
    private fun inputCheck(
        nameShoe: String,
        priceShoe: String,
        distributorShoe: String,
        amountShoe: String
    ): Boolean {
        return !(TextUtils.isEmpty(nameShoe) && TextUtils.isEmpty(priceShoe) && TextUtils.isEmpty(distributorShoe) && TextUtils.isEmpty(amountShoe))
    }
}