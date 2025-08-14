package com.nexgen.flexiBank.module.view.auth.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentCountryBottomSheetBinding
import com.nexgen.flexiBank.module.view.auth.adapter.CountryAdapter
import com.nexgen.flexiBank.module.view.auth.model.Country

class CountryBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCountryBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var countryAdapter: CountryAdapter
    private var countries = mutableListOf<Country>()
    private var filteredCountries = mutableListOf<Country>()
    private var onCountrySelectedListener: ((Country) -> Unit)? = null
    private var selectedCountry: Country? = null

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme

    companion object {
        fun newInstance(
            selectedCountry: Country, listener: (Country) -> Unit
        ): CountryBottomSheetFragment {
            return CountryBottomSheetFragment().apply {
                this.selectedCountry = selectedCountry
                onCountrySelectedListener = listener
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set expanded state for bottom sheet
        val bottomSheetBehavior =
            com.google.android.material.bottomsheet.BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state =
            com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.skipCollapsed = false
        setupCountryList()
        setupUI()
    }

    private fun setupUI() {
        binding.btnDone.setOnClickListener {
            dismiss()
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCountries(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterCountries(query: String) {
        filteredCountries.clear()
        if (query.isEmpty()) {
            filteredCountries.addAll(countries)
        } else {
            val searchQuery = query.lowercase()
            filteredCountries.addAll(countries.filter {
                it.name.lowercase().contains(searchQuery) || it.dialCode.contains(searchQuery)
            })
        }
    }

    private fun setupCountryList() {
        // Sample data - in a real app, this would come from a repository
        countries = mutableListOf(
            Country("Cambodia", "+855", "KH", R.drawable.img_khmer_flag),
            Country("United States", "+1", "US", R.drawable.img_uk_flag),
            Country("China", "+86", "CN", R.drawable.img_china_flag),
        )

        filteredCountries.addAll(countries)

        countryAdapter = CountryAdapter(
            filteredCountries,
            selectedCountry,
        ) { country ->
            // Update the adapter with the new selection
            val oldSelectedCode = selectedCountry
            selectedCountry = country

            // Update the UI to show selection before dismissing
            updateSelectionInAdapter(oldSelectedCode, country)

            // Wait a moment to show the selection before dismissing
            binding.root.postDelayed({
                onCountrySelectedListener?.invoke(country)
                dismiss()
            }, 200) // Short delay to see the selection
        }

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }
    }

    private fun updateSelectionInAdapter(oldCode: Country?, newCode: Country?) {
        val oldPosition = countries.indexOfFirst { it.code == oldCode?.code }
        val newPosition = countries.indexOfFirst { it.code == newCode?.code }

        if (oldPosition != -1) {
            countryAdapter.notifyItemChanged(oldPosition)
        }

        if (newPosition != -1) {
            countryAdapter.notifyItemChanged(newPosition)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
