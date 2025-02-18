package com.idz.rentit.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentit.listeners.authentication.OnItemClickListener
import com.idz.rentit.viewholders.PropertyItemViewHolder

abstract class PropertyItemAdapter<T>(
    protected val layoutInflater: LayoutInflater,
    protected var propertyItemList: List<T>
) : RecyclerView.Adapter<PropertyItemViewHolder<T>>() {

    protected var listener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    fun setMovieItemList(movieItemList: List<T>) {
        this.propertyItemList = movieItemList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return propertyItemList.size
    }
}
