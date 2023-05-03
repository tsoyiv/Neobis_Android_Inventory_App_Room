package com.example.shop_app.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_app.R
import com.example.shop_app.model.Shoe
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var shoeList = emptyList<Shoe>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = shoeList[position]
        holder.itemView.name_id_item.text = currentItem.name
        holder.itemView.price_id_tem.text = currentItem.price
        holder.itemView.amount_id_tem.text = currentItem.amount
        holder.itemView.distri_id_tem.text = currentItem.distributor
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