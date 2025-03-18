package com.idz.rentit.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentit.listeners.authentication.OnItemClickListener
import com.idz.rentit.repository.models.Property

abstract class PropertyItemViewHolder<T>(
    itemView: View,
    val listener: ((Int?, Property?) -> Unit)? // Change the type here and add val
) : RecyclerView.ViewHolder(itemView) {
    abstract var propertyItemLocation: TextView
    abstract var propertyItemPrice: TextView
    abstract var propertyItemDescription: TextView
    abstract var propertyItemImg: ImageView
    abstract var propertyItemUser: TextView

    abstract fun bindPropertyItem(propertyItem: T)
}