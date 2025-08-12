package com.nexgen.flexiBank.module.view.bakongQRCode

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexgen.flexiBank.databinding.SupportedQrItemViewBinding

class SupportedQrAdapter(
    private val supportedQRCodes: List<SupportedQRCode>,
    private val onItemClick: (SupportedQRCode) -> Unit
) : RecyclerView.Adapter<SupportedQrAdapter.SupportedQRViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupportedQRViewHolder {
        val binding = SupportedQrItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SupportedQRViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SupportedQRViewHolder, position: Int) {
        val supportedQRCode = supportedQRCodes[position]
        holder.bind(supportedQRCode)
    }

    override fun getItemCount(): Int = supportedQRCodes.size

    inner class SupportedQRViewHolder(private val binding: SupportedQrItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(supportedQRCode: SupportedQRCode) {
            supportedQRCode.imageUrl?.let { imageUrl ->
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(binding.imgImage)
            }
            binding.root.setOnClickListener {
                onItemClick.invoke(supportedQRCode)
            }
        }
    }

    data class SupportedQRCode(
        val imageUrl: String? = null,
    )

}

