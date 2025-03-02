package com.idz.rentit.adapters

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentit.listeners.authentication.OnItemClickListener
import com.idz.rentit.viewHolders.PropertyItemViewHolder

abstract class PropertyItemAdapter<T>(
    protected val layoutInflater: LayoutInflater,
    var propertyItemList: List<T>
) : RecyclerView.Adapter<PropertyItemViewHolder<T>>() {

    protected var listener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    override fun getItemCount(): Int {
        return propertyItemList.size
    }
}
