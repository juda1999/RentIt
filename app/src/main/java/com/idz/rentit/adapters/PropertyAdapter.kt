package com.idz.rentit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idz.rentit.repository.models.Property
import com.idz.rentIt.R
import com.idz.rentit.viewHolders.PropertyItemViewHolder
import com.idz.rentit.viewHolders.PropertyViewHolder

class PropertyAdapter(
    layoutInflater: LayoutInflater,
    propertyItemList: List<Property>
) : PropertyItemAdapter<Property>(layoutInflater, propertyItemList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view: View = layoutInflater.inflate(R.layout.item_list_row_property, parent, false)
        return PropertyViewHolder(view, listener)
    }

    override fun onBindViewHolder(propertyItemHolder: PropertyItemViewHolder<Property>, position: Int) {
        val property = propertyItemList[position]
        propertyItemHolder.bindPropertyItem(property)
    }
}
