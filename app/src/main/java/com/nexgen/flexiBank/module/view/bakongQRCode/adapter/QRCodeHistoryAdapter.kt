package com.nexgen.flexiBank.module.view.bakongQRCode.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nexgen.flexiBank.databinding.ItemQrCodeHistoryBinding
import com.nexgen.flexiBank.databinding.ItemQrCodeHistoryByDateBinding
import com.nexgen.flexiBank.module.view.auth.model.AccountModel
import com.nexgen.flexiBank.module.view.auth.model.QRCodeHistoryModel

class NestedHistoryListAdapter(private val items: List<AccountModel>) :
    RecyclerView.Adapter<NestedHistoryListAdapter.NestedItemViewHolder>() {

    class NestedItemViewHolder(val binding: ItemQrCodeHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedItemViewHolder {
        val binding =
            ItemQrCodeHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return NestedItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NestedItemViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvAccountName.text = item.accountName
            tvAccountNumber.text = item.accountNumber
            Glide.with(holder.itemView.context)
                .load(item.avatar)
                .circleCrop()
                .into(ivProfile)
        }
    }

    override fun getItemCount(): Int = items.size
}

class QRCodeHistoryAdapter(
    private val countries: List<QRCodeHistoryModel>,
    private var selectedCountry: QRCodeHistoryModel? = null,
    private val onCountrySelected: (QRCodeHistoryModel) -> Unit,
) : RecyclerView.Adapter<QRCodeHistoryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: ItemQrCodeHistoryByDateBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding =
            ItemQrCodeHistoryByDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val qrCodeHistoryByDate = countries[position]
        with(holder.binding) {
            tvDate.text = qrCodeHistoryByDate.date
            lvAccount.adapter = NestedHistoryListAdapter(qrCodeHistoryByDate.list)
            root.setOnClickListener {
                onCountrySelected(qrCodeHistoryByDate)
            }
        }
    }

    override fun getItemCount(): Int = countries.size

}
