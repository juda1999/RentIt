package com.idz.rentit.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.idz.rentIt.R
import com.idz.rentit.repository.Repository
import com.idz.rentit.repository.models.Property
import com.squareup.picasso.Picasso

class PropertyViewHolder(
    itemView: View,
    listener: ((Int?, Property?) -> Unit)? // Change the type here
) : PropertyItemViewHolder<Property>(itemView, listener) {

    override var propertyItemLocation: TextView = itemView.findViewById(R.id.item_list_row_location)
    override var propertyItemDescription: TextView = itemView.findViewById(R.id.item_list_row_description)
    override var propertyItemPrice: TextView = itemView.findViewById(R.id.item_list_row_price)
    override var propertyItemUser: TextView = itemView.findViewById(R.id.item_list_row_user)

    override fun bindPropertyItem(propertyItem: Property) {
        if (propertyItem.imageUrl.isNotBlank()) {
            Picasso.get().load(propertyItem.imageUrl)
                .placeholder(R.drawable.home)
                .into(propertyItemImg)
        } else {
            propertyItemImg.setImageResource(R.drawable.home)
        }
        Repository.repositoryInstance.getFirebaseModel().userExecutor.getUserById(propertyItem.userId) { user ->
            propertyItemUser.text = user.firstName
        }
        propertyItemLocation.text = propertyItem.location
        propertyItemPrice.text = "rent is ${propertyItem.price}$"
        propertyItemDescription.text = propertyItem.description
        itemView.setOnClickListener {
            listener?.invoke(adapterPosition, propertyItem)
        }
    }

    override var propertyItemImg: ImageView = itemView.findViewById(R.id.item_list_row_img)
}