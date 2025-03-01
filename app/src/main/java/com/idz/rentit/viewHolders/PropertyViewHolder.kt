package com.idz.rentit.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.idz.rentIt.R
import com.idz.rentit.listeners.authentication.OnItemClickListener
import com.idz.rentit.repository.models.Property
import com.squareup.picasso.Picasso

class PropertyViewHolder(
    itemView: View,
    listener: OnItemClickListener?
) : PropertyItemViewHolder<Property>(itemView, listener) {

    override var propertyItemNumberOfRooms = itemView.findViewById<TextView>(R.id.item_list_row_name_tv)
    override var propertyItemPrice = itemView.findViewById<TextView>(R.id.item_list_row_rating_tv)
    override var propertyItemDescription = itemView.findViewById<TextView>(R.id.item_list_row_comment_tv)
    override var propertyItemImg = itemView.findViewById<ImageView>(R.id.item_list_row_img)

    override fun bindPropertyItem(propertyItem: Property) {
        if (propertyItem.imageUrl.isNotBlank()) {
            Picasso.get().load(propertyItem.imageUrl)
                .placeholder(R.drawable.avatar)
                .into(propertyItemImg)
        } else {
            propertyItemImg.setImageResource(R.drawable.avatar)
        }
        propertyItemNumberOfRooms.text = propertyItem.numberOfRooms.toString()
        propertyItemPrice.text = propertyItem.price.toString()
    }
}
