package com.nexgen.flexiBank.module.view.verifyDocument

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.nexgen.flexiBank.R

class PermissionHandler(private val fragment: Fragment, private val context: Activity) {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var permission: String
    private var permissionEnable = false
    fun handlePermission() {
        permissionLauncher = fragment.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                permissionEnable = true
            } else {
                permissionEnable = false
                moveToPermission(
                    context, "Access files permission"
                )
            }
        }
        permission = when {
            Build.VERSION.SDK_INT <= 32 -> {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            }

            else -> {
                Manifest.permission.READ_MEDIA_IMAGES
            }
        }

        permissionLauncher.launch(permission)
    }

    private fun moveToPermission(activity: Activity, title: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val body: TextView = dialog.findViewById(R.id.errorText)
        val okButton: Button = dialog.findViewById(R.id.okButton)
        okButton.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }
        body.text = title
        dialog.show()
    }

}