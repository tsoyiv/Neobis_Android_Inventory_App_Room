package com.example.shop_app.fragments.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_app.R
import com.example.shop_app.model.Shoe
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var shoeList = emptyList<Shoe>()
    private var onClickListener: OnClickListener? = null

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

        holder.itemView.rowLayout.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("shoe", currentItem)
            it.findNavController().navigate(R.id.action_homePageFragment_to_updateFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return shoeList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(shoe: List<Shoe>) {
        this.shoeList = shoe
        notifyDataSetChanged()
    }
}