package com.idz.rentit.viewHolders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentit.listeners.authentication.OnItemClickListener

abstract class PropertyItemViewHolder<T>(
    itemView: View,
    listener: OnItemClickListener?
) : RecyclerView.ViewHolder(itemView) {
     protected open lateinit var propertyItemDescription: TextView
     protected open lateinit var propertyItemPrice: TextView
     protected open lateinit var propertyItemNumberOfRooms: TextView
     protected open lateinit var propertyItemImg: ImageView

    init {
        itemView.setOnClickListener {
            val position = adapterPosition
            Log.d("TAG", "row clicked $position")
            listener?.onItemClick(position)
        }
    }

    abstract fun bindPropertyItem(propertyItem: T)
}
