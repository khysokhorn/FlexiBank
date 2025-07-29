package com.nexgen.flexiBank.module.view.auth.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexgen.flexiBank.databinding.ItemCountryBinding
import com.nexgen.flexiBank.module.view.auth.model.Country

class CountryAdapter(
    private val countries: List<Country>,
    private var selectedCountry: Country? = null,
    private val onCountrySelected: (Country) -> Unit,
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        with(holder.binding) {
            tvCountryName.text = country.name
            tvDialCode.text = country.dialCode
            ivCountryFlag.setImageResource(country.flagResource)

            // Set the visibility of the check mark
            if (country == selectedCountry) {
                imgCheck.visibility = android.view.View.VISIBLE
            } else {
                imgCheck.visibility = android.view.View.GONE
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
