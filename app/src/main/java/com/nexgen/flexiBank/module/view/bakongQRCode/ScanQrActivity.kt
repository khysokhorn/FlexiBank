package com.nexgen.flexiBank.module.view.bakongQRCode

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import ch.ubique.qrscanner.scanner.BarcodeFormat
import ch.ubique.qrscanner.state.DecodingState
import ch.ubique.qrscanner.zxing.decoder.GlobalHistogramImageDecoder
import ch.ubique.qrscanner.zxing.decoder.HybridImageDecoder
import com.nexgen.flexiBank.databinding.ActivityScanQrBinding
import com.nexgen.flexiBank.module.view.bakongQRCode.componnet.ScannerFrame
import com.nexgen.flexiBank.module.view.bakongQRCode.fragment.QRCodeHistoryBottomSheetFragment
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.ScanQrViewModel
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.navigation.ComposeNavigationActivity
import com.nexgen.flexiBank.navigation.Screen
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository
import timber.log.Timber

class ScanQrActivity : BaseMainActivity<ScanQrViewModel, ActivityScanQrBinding, AppRepository>() {
    private var isFlashEnabled = false

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_128)
        binding.qrScannerFrame.apply {
            setContent {
                ScannerFrame(modifier = Modifier.wrapContentSize())
            }
        }
        binding.qrScanner.setImageDecoders(
            GlobalHistogramImageDecoder(formats), HybridImageDecoder(formats)
        )
        binding.qrScanner.setScannerCallback { state ->
            when (state) {
                is DecodingState.NotFound -> binding.decodingState.text = "Scanning"
                is DecodingState.Decoded -> {
                    binding.decodingState.text = state.content
                    startActivity(Intent(this, ComposeNavigationActivity::class.java).apply {
                        putExtra("start_destination", Screen.KhQRInputAmount.route)
                    })
                }

                is DecodingState.Error -> binding.decodingState.text = "Error: ${state.errorCode}"
            }
        }
        binding.imgFlash.setOnClickListener {
            isFlashEnabled = !isFlashEnabled
            binding.qrScanner.setFlash(isFlashEnabled)
        }
        val supportedQrAdapter = SupportedQrAdapter(
            listOf(
                SupportedQrAdapter.SupportedQRCode(
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bb/KHQR_Logo.png"
                ), SupportedQrAdapter.SupportedQRCode(
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/77/VietQR_Logo.png"
                ), SupportedQrAdapter.SupportedQRCode(
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/Thai_QR_Logo.svg/960px-Thai_QR_Logo.svg.png?20250310160238"
                )
            )
        ) {}
        binding.rvSupportedQr.adapter = supportedQrAdapter
        binding.imgClose.setOnClickListener {
            finish()
        }
        binding.btnUploadQr.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.imgHistory.setOnClickListener {
            showCountryBottomSheet()
        }
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Timber.tag("PhotoPicker").d("Selected URI: $uri")
        } else {
            Timber.tag("PhotoPicker").d("No media selected")
        }
    }

    private fun showCountryBottomSheet() {
        val bottomSheet = QRCodeHistoryBottomSheetFragment.newInstance { country ->
        }
        bottomSheet.show(this.supportFragmentManager, "CountryBottomSheet")
    }

    override fun getViewModel(): Class<ScanQrViewModel> = ScanQrViewModel::class.java

    override fun getActivityBinding(): ActivityScanQrBinding =
        ActivityScanQrBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))

}