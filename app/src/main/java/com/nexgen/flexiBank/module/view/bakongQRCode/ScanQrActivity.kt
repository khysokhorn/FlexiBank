package com.nexgen.flexiBank.module.view.bakongQRCode

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ch.ubique.qrscanner.scanner.BarcodeFormat
import ch.ubique.qrscanner.state.DecodingState
import ch.ubique.qrscanner.zxing.decoder.GlobalHistogramImageDecoder
import ch.ubique.qrscanner.zxing.decoder.HybridImageDecoder
import com.nexgen.flexiBank.databinding.ActivityScanQrBinding
import com.nexgen.flexiBank.module.view.bakongQRCode.fragment.QRCodeHistoryBottomSheetFragment
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.ScanQrViewModel
import com.nexgen.flexiBank.module.view.base.BaseMainActivity
import com.nexgen.flexiBank.navigation.KhQRCodeNavigationActivity
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

        binding.qrScanner.setImageDecoders(
            GlobalHistogramImageDecoder(formats), HybridImageDecoder(formats)
        )
        binding.qrScanner.setScannerCallback { state ->
            when (state) {
                is DecodingState.NotFound -> binding.decodingState.text = "Scanning"
                is DecodingState.Decoded -> {
                    if (viewModel.qrCodeDetected) {
                        viewModel.qrCodeDetected = false
                        binding.decodingState.text = state.content
                        startActivity(Intent(this, KhQRCodeNavigationActivity::class.java).apply {
                            putExtra("start_destination", Screen.KhQRInputAmount.route)
                            finish()
                        })
                    }
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

    override fun onCreateView(
        parent: View?, name: String, context: Context, attrs: AttributeSet
    ): View? {
        cameraPermissionHandler()
        return super.onCreateView(parent, name, context, attrs)
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

    fun cameraPermissionHandler() {
        // Check if the app has camera permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Timber.tag("Permission").d("Camera permission is granted")
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE
            )
            Timber.tag("Permission").d("Camera permission is NOT granted")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                binding.qrScanner.activateCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100

    }

}