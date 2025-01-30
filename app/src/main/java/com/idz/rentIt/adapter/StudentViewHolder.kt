package com.idz.rentIt.adapter

import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.idz.rentIt.OnItemClickListener
import com.idz.rentIt.R
import com.idz.rentIt.databinding.StudentListRowBinding
import com.idz.rentIt.model.Post
import com.squareup.picasso.Picasso

class StudentViewHolder(
    private val binding: StudentListRowBinding,
    listener: OnItemClickListener?
    ): RecyclerView.ViewHolder(binding.root) {

    private var post: Post? = null

        init {

            binding.checkBox.apply {
                setOnClickListener { view ->
                    (tag as? Int)?.let { tag ->
                        post?.isFurnished = (view as? CheckBox)?.isChecked ?: false
                    }
                }
            }

            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
                listener?.onItemClick(post)
            }
        }

        fun bind(post: Post?, position: Int) {
            this.post = this.post
            binding.nameTextView.text = this.post?.address
            binding.idTextView.text = this.post?.id
            binding.checkBox.apply {
                isChecked = this@StudentViewHolder.post?.isFurnished ?: false
                tag = position
            }

            this.post?.photoUrl?.let { avatarUrl ->
                val url = avatarUrl.ifBlank { return }
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.avatar)
                    .into(binding.imageView)
            }
        }
    }