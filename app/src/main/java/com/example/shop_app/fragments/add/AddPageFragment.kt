package com.example.shop_app.fragments.add

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
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

    companion object {
        val IMAGE_REQUEST_CODE = 1
    }
    private var bitmap: Bitmap? = null
    private lateinit var mShoeViewModel: ShoeViewModel
    private lateinit var binding: ActivityMainBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        view.add_image_button.setOnClickListener {
            pickImage()
        }

        return view
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val addImageButton = binding.root.findViewById<ImageButton>(R.id.add_image_button)
                addImageButton.loadImage(bitmap)
                this.bitmap = bitmap
            }
        }
    fun ImageView.loadImage(bitmap: Bitmap) {
        setImageBitmap(bitmap)
    }
    private val requestSinglePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContent.launch()
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            val path: Uri? = data?.data
            view?.add_image_button?.setImageURI(path)
        }
    }
    private fun insertDataToDatabase() {
        val image_shoe = add_image_button
        val name_shoe = item_name_text.text.toString()
        val price_shoe = item_price_text.text.toString()
        val distributor_shoe = item_distributor_text.text.toString()
        val amount_shoe = item_amount_text.text.toString()

        if (inputCheck(name_shoe, price_shoe, distributor_shoe, amount_shoe, image_shoe)) {
            val shoe = Shoe(0, name_shoe, price_shoe, distributor_shoe, amount_shoe, image_shoe.drawToBitmap())
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
        amountShoe: String,
        image_shoe: ImageButton
    ): Boolean {
        return !(TextUtils.isEmpty(nameShoe) && TextUtils.isEmpty(priceShoe) && TextUtils.isEmpty(
            distributorShoe
        ) && TextUtils.isEmpty(amountShoe))
    }
}