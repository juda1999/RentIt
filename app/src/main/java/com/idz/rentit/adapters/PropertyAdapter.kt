package com.idz.rentit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentit.repository.models.Property
import com.idz.rentIt.R
import com.idz.rentit.viewHolders.PropertyItemViewHolder
import com.idz.rentit.viewHolders.PropertyViewHolder

class PropertyAdapter(
    val layoutInflater: LayoutInflater,
    private val propertyItemList: MutableList<Property> // Mutable list
) : RecyclerView.Adapter<PropertyItemViewHolder<Property>>() {
    var listener: ((Int?) -> Unit)? = null
    fun setOnItemClickListener(listener: ((Int?) -> Unit)?) {
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_list_row_property, parent, false)
        return PropertyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: PropertyItemViewHolder<Property>, position: Int) {
        val property = propertyItemList[position]
        holder.bindPropertyItem(property)
    }

    override fun getItemCount(): Int {
        return propertyItemList.size
    }

    // Method to update the list
    fun updatePropertyList(newPropertyList: List<Property>) {
        propertyItemList.clear()
        propertyItemList.addAll(newPropertyList)
        notifyDataSetChanged()
    }
}