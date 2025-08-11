package com.nexgen.flexiBank.module.view.bakongQRCode

import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import ch.ubique.qrscanner.scanner.BarcodeFormat
import ch.ubique.qrscanner.state.DecodingState
import ch.ubique.qrscanner.zxing.decoder.GlobalHistogramImageDecoder
import ch.ubique.qrscanner.zxing.decoder.HybridImageDecoder
import com.nexgen.flexiBank.databinding.ActivityScanQrBinding
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.ScanQrViewModel
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository


class ScanQrActivity : BaseMainActivity<ScanQrViewModel, ActivityScanQrBinding, AppRepository>() {
    private var isFlashEnabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_128)

        binding.qrScanner.setImageDecoders(
            GlobalHistogramImageDecoder(formats), HybridImageDecoder(formats)
        )
        binding.qrScanner.setScannerCallback { state ->
            when (state) {
                is DecodingState.NotFound -> binding.decodingState.text = "Scanning"
                is DecodingState.Decoded -> {
                    binding.decodingState.text = state.content
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
    }

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun getViewModel(): Class<ScanQrViewModel> = ScanQrViewModel::class.java

    override fun getActivityBinding(): ActivityScanQrBinding =
        ActivityScanQrBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))

}