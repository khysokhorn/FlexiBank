package com.nexgen.flexiBank.module.view.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexgen.flexiBank.databinding.PaymentItemViewBinding

class UpCommingPaymentAdapter(
    private val contacts: List<UpComingPayment>,
    private val onItemClick: (UpComingPayment) -> Unit
) : RecyclerView.Adapter<UpCommingPaymentAdapter.UpComingPaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpComingPaymentViewHolder {
        val binding = PaymentItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpComingPaymentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpComingPaymentViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = contacts.size

    inner class UpComingPaymentViewHolder(private val binding: PaymentItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: UpComingPayment) {
            binding.tvPaymentName.text = contact.name
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

    data class UpComingPayment(
        val name: String,
        val imageUrl: String? = null,
        val amount: Double = 0.0,
        val createdDate: String = ""
    )
}