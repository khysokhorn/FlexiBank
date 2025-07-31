package com.nexgen.flexiBank.module.view.qrCode

import android.os.Bundle
import ch.ubique.qrscanner.scanner.BarcodeFormat
import ch.ubique.qrscanner.state.DecodingState
import ch.ubique.qrscanner.zxing.decoder.GlobalHistogramImageDecoder
import ch.ubique.qrscanner.zxing.decoder.HybridImageDecoder
import com.nexgen.flexiBank.databinding.ActivityScanQrBinding
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.module.view.qrCode.viewModel.ScanQrViewModel
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class ScanQrActivity : BaseMainActivity<ScanQrViewModel, ActivityScanQrBinding, AppRepository>() {
    private var isFlashEnabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val formats = listOf(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_128)

        binding.qrScanner.setImageDecoders(
            GlobalHistogramImageDecoder(formats),
            HybridImageDecoder(formats)
        )

        binding.qrScanner.setScannerCallback { state ->
            when (state) {
                is DecodingState.NotFound -> binding.decodingState.text = "Scanning"
                is DecodingState.Decoded -> binding.decodingState.text = state.content
                is DecodingState.Error -> binding.decodingState.text = "Error: ${state.errorCode}"
            }
        }

        binding.cameraFlash.setOnClickListener {
            isFlashEnabled = !isFlashEnabled
            binding.qrScanner.setFlash(isFlashEnabled)
        }

        binding.cameraZoom.addOnChangeListener { _, value, _ ->
            binding.qrScanner.setLinearZoom(value)
        }
    }

    override fun getViewModel(): Class<ScanQrViewModel> = ScanQrViewModel::class.java

    override fun getActivityBinding(): ActivityScanQrBinding =
        ActivityScanQrBinding.inflate(layoutInflater)

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))
}