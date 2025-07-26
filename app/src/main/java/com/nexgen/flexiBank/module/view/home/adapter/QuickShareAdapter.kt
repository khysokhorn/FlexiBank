package com.nexgen.flexiBank.module.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.QuickShareItemViewBinding

class QuickShareAdapter(
    private val contacts: List<QuickShareContact>,
    private val onItemClick: (QuickShareContact) -> Unit
) : RecyclerView.Adapter<QuickShareAdapter.QuickShareViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickShareViewHolder {
        val binding = QuickShareItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QuickShareViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuickShareViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = contacts.size

    inner class QuickShareViewHolder(private val binding: QuickShareItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: QuickShareContact) {
            binding.tvName.text = contact.name
            contact.isActive.let {
                if (it) {
                    binding.imgImage.setBackgroundResource(R.drawable.bg_circle_blue_16)
                } else {
                    binding.imgImage.setBackgroundResource(R.drawable.bg_circle_gray_16)
                }
            }
            contact.imageUrl?.let { imageUrl ->
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .circleCrop()
                    .into(binding.imgImage)
            }
            itemView.setOnClickListener {
                onItemClick(contact)
            }
        }
    }

    data class QuickShareContact(
        val id: String,
        val name: String,
        val imageUrl: String? = null,
        val isActive: Boolean = false
    )
}