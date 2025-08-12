package com.nexgen.flexiBank.module.view.bakongQRCode

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ShareReceiverQRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent?.action == Intent.ACTION_SEND) {
            val imageUri = getSharedImageUri(intent)
            if (imageUri != null) {
                Toast.makeText(this, "Image URI: $imageUri", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Image URI: null", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getSharedImageUri(intent: Intent): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)?.let { return it }
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)?.let { return it }
        }

        intent.clipData?.let { clip ->
            if (clip.itemCount > 0) {
                return clip.getItemAt(0).uri
            }
        }

        return null
    }

}
