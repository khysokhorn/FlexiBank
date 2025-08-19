package com.nexgen.flexiBank.module.view.bakongQRCode.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.databinding.FragmentQrCodeHistoryByDateBottomSheetBinding
import com.nexgen.flexiBank.module.view.auth.model.AccountModel
import com.nexgen.flexiBank.module.view.auth.model.QRCodeHistoryModel
import com.nexgen.flexiBank.module.view.bakongQRCode.adapter.QRCodeHistoryAdapter

class QRCodeHistoryBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentQrCodeHistoryByDateBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var countryAdapter: QRCodeHistoryAdapter
    private var countries = mutableListOf<QRCodeHistoryModel>()
    private var filteredCountries = mutableListOf<QRCodeHistoryModel>()
    private var onCountrySelectedListener: ((QRCodeHistoryModel) -> Unit)? = null
    private var selectedCountry: QRCodeHistoryModel? = null

    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme

    companion object {
        fun newInstance(
            listener: (QRCodeHistoryModel) -> Unit
        ): QRCodeHistoryBottomSheetFragment {
            return QRCodeHistoryBottomSheetFragment().apply {
                onCountrySelectedListener = listener
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQrCodeHistoryByDateBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set expanded state for bottom sheet
        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.skipCollapsed = false
        countryAdapter = QRCodeHistoryAdapter(
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
            filteredCountries.addAll(countries.filter { qrCode ->
                qrCode.list.any { accountModel ->
                    accountModel.accountName.contains(searchQuery)
                }
            })
        }
    }

    private fun setupCountryList() {
        countries = mutableListOf(
            QRCodeHistoryModel(
                "Today", listOf(
                    AccountModel(
                        "Lay Sengly",
                        "USD",
                        "001 369 963",
                        "https://img.freepik.com/premium-psd/stylish-young-man-3d-icon-avatar-people_431668-1607.jpg",
                    ),
                )
            ),
        )

        filteredCountries.addAll(countries)

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }
    }

    private fun updateSelectionInAdapter(
        oldCode: QRCodeHistoryModel?, newCode: QRCodeHistoryModel?
    ) {
        val oldPosition = countries.indexOfFirst { it.list == oldCode?.list }
        val newPosition = countries.indexOfFirst { it.list == newCode?.list }

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
