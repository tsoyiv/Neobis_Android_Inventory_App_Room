package com.example.shop_app.fragments.list

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shop_app.R
import com.example.shop_app.RecyclerListener
import com.example.shop_app.model.Shoe
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_dialog_for_main_fragment.view.*
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlinx.android.synthetic.main.fragment_add_page.view.*

class ListAdapter(private val listener: RecyclerListener) : RecyclerView.Adapter<ListAdapter.MyViewHolder>(){

    private var shoeList = emptyList<Shoe>()
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    fun setList(mList: List<Shoe>) {
        this.shoeList = mList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shoeList[position]
        holder.itemView.name_id_item.text = currentItem.name
        holder.itemView.price_id_tem.text = currentItem.price
        holder.itemView.distri_id_tem.text = currentItem.distributor
        holder.itemView.amount_id_tem.text = currentItem.amount
        holder.itemView.image_on_item.load(shoeList[position].shoeImage)

        holder.itemView.setOnClickListener {
            val action = HomePageFragmentDirections.actionHomePageFragmentToUpdateFragment()
            action.currentShoe = currentItem
            holder.itemView.findNavController().navigate(action)
        }
//        holder.itemView.setOnClickListener {
//            val action = ArchivePageFragmentDirections.actionArchivePageFragmentToUpdateFragment()
//            action.currentShoe = currentItem
//            holder.itemView.isEnabled = false
//        }


        holder.itemView.action_button_item.setOnClickListener {
            listener.archiveProduct(currentItem)
//            val view = LayoutInflater.from(holder.itemView.context)
//                .inflate(R.layout.bottom_dialog_for_main_fragment, null)
//            val dialog = BottomSheetDialog(holder.itemView.context)
//            dialog.setContentView(view)
//            dialog.show()
        }
    }
//    private fun alertDialogArchive() {
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Архивировать Air Jordn из каталога?")
//        builder.setPositiveButton("Да") { _,_ ->
//        }
//        builder.setNegativeButton("Нет") { _,_ ->
//            builder.setCancelable(true)
//        }
//        builder.create().show()
//    }

    override fun getItemCount(): Int {
        return shoeList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(shoe: List<Shoe>) {
        this.shoeList = shoe
        notifyDataSetChanged()
    }
}