package com.example.shop_app.fragments.update

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shop_app.R
import com.example.shop_app.databinding.ActivityMainBinding
import com.example.shop_app.fragments.add.AddPageFragment
import com.example.shop_app.model.Shoe
import com.example.shop_app.viewmodel.ShoeViewModel
import kotlinx.android.synthetic.main.fragment_add_page.*
import kotlinx.android.synthetic.main.fragment_add_page.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.cancel_text_btn

class UpdateFragment : Fragment() {

    private val args: UpdateFragmentArgs by navArgs()
    private lateinit var mShoeViewModel: ShoeViewModel
    private lateinit var editText: EditText
    private lateinit var binding: ActivityMainBinding
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mShoeViewModel = ViewModelProvider(this).get(ShoeViewModel::class.java)

        view.arrow_update_fragment_back.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_homePageFragment)
        }
        view.cancel_text_btn.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_homePageFragment)
        }

        args.currentShoe?.let { shoe ->
            view.item_updateName_text.setText(shoe.name)
            view.item_updatePrice_text.setText(shoe.price)
            view.item_updateDistributor_text.setText(shoe.distributor)
            view.item_updateAmount_text.setText(shoe.amount)
            view.update_image_button.setImageBitmap(args.currentShoe!!.shoeImage)
            view.update_image_button.setOnClickListener {
                pickImage()
            }
            view.update_button.setOnClickListener {
                alertDialogSave()
            }
        }

        return view
    }

    private fun alertDialogSave() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Сохранить изменения?")
        builder.setPositiveButton("Сохранить") { _,_ ->
            updateItem()
        }
        builder.setNegativeButton("Отмена") { _,_ ->
            builder.setCancelable(true)
        }
        builder.create().show()
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, AddPageFragment.IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddPageFragment.IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val path: Uri? = data?.data
            view?.update_image_button?.setImageURI(path)
        }
    }
    private val getContent =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                val updateImage = binding.root.findViewById<ImageButton>(R.id.update_image_button)
                updateImage.loadImage(bitmap)
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
        val image = update_image_button

        if (inputCheck(name, price, distributor, amount)) {
            val updatedShoe = args.currentShoe?.let { Shoe(it.id, name, price, distributor, amount, image.drawToBitmap(), args.currentShoe!!.isArchived) }
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