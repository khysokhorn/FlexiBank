package com.nexgen.flexiBank.module.view.bakongQRCode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nexgen.flexiBank.module.view.bakongQRCode.viewModel.KhQrInputAmountViewModel
import com.nexgen.flexiBank.module.view.base.BaseComposeActivity
import com.nexgen.flexiBank.network.ApiInterface
import com.nexgen.flexiBank.repository.AppRepository

class KhQRInputAmountActivity : BaseComposeActivity<KhQrInputAmountViewModel, AppRepository>() {

    @Composable
    override fun ComposeContent() {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Body()
        }
    }

    @Composable
    fun Body() {
        Column(
        ) {}
    }

    override fun getViewModel(): Class<KhQrInputAmountViewModel> =
        KhQrInputAmountViewModel::class.java

    override fun getRepository(): AppRepository =
        AppRepository(remoteDataSource.buildApi(this, ApiInterface::class.java))

    @Preview(showBackground = true)
    @Composable
    override fun ContentPreview() {
        ComposeContent()
    }

}