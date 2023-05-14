package com.example.shop_app.fragments.add

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shop_app.R
import com.example.shop_app.databinding.ActivityMainBinding
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import kotlinx.android.synthetic.main.fragment_add_page.*
import kotlinx.android.synthetic.main.fragment_add_page.view.*

class AddPageFragment : Fragment() {

    private lateinit var mShoeViewModel: ShoeViewModel
    //private lateinit var binding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_page, container, false)

        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        view.add_button.setOnClickListener {
            insertDataToDatabase()
        }
        view.cancel_text_btn.setOnClickListener {
            findNavController().navigate(R.id.action_addPageFragment_to_homePageFragment)
        }
        view.arrow_add_fragment_back.setOnClickListener {
            findNavController().navigate(R.id.action_addPageFragment_to_homePageFragment)
        }
        view.add_imgae_button.setOnClickListener {
            pickImage()
        }

        return view
    }
    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "/image*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val path: Uri? = data?.data
            view?.add_imgae_button?.setImageURI(path)
        }
    }
    private fun insertDataToDatabase() {
        val name_shoe = item_name_text.text.toString()
        val price_shoe = item_price_text.text.toString()
        val distributor_shoe = item_distributor_text.text.toString()
        val amount_shoe = item_amount_text.text.toString()

        if (inputCheck(name_shoe, price_shoe, distributor_shoe, amount_shoe)) {
            val shoe = Shoe(0, name_shoe, price_shoe, distributor_shoe, amount_shoe)
            mShoeViewModel.addShoe(shoe)
            Toast.makeText(requireContext(), "Added", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addPageFragment_to_homePageFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out", Toast.LENGTH_LONG).show()
        }
    }


    private fun inputCheck(
        nameShoe: String,
        priceShoe: String,
        distributorShoe: String,
        amountShoe: String
    ): Boolean {
        return !(TextUtils.isEmpty(nameShoe) && TextUtils.isEmpty(priceShoe) && TextUtils.isEmpty(
            distributorShoe
        ) && TextUtils.isEmpty(amountShoe))
    }
}