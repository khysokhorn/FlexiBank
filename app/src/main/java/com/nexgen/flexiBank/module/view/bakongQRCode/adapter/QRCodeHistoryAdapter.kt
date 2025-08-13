package com.nexgen.flexiBank.module.view.bakongQRCode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexgen.flexiBank.databinding.ItemCountryBinding
import com.nexgen.flexiBank.module.view.auth.model.QRCodeHistoryModel

class QRCodeHistoryAdapter(
    private val countries: List<QRCodeHistoryModel>,
    private var selectedCountry: QRCodeHistoryModel? = null,
    private val onCountrySelected: (QRCodeHistoryModel) -> Unit,
) : RecyclerView.Adapter<QRCodeHistoryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        with(holder.binding) {
//            tvCountryName.text = country.name
//            tvDialCode.text = country.dialCode
//            ivCountryFlag.setImageResource(country.flagResource)

            // Set the visibility of the check mark
            if (country == selectedCountry) {
                imgCheck.visibility = View.VISIBLE
            } else {
                imgCheck.visibility = View.GONE
            }

            root.setOnClickListener {
                // Change to the current country without changing adapter state
                // The fragment will handle updating the adapter
                onCountrySelected(country)
            }
        }
    }

    override fun getItemCount(): Int = countries.size

}
