package com.nexgen.flexiBank.utils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import com.nexgen.flexiBank.R
import timber.log.Timber

fun moveToPermission(activity: Activity, title: String) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.dialog_error)
    dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
    dialog.window?.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
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

const val FLEXI_BANK_LOG_TAG = "FLEXIBANKLog"

fun errorLog(error: Throwable?) = error?.let {
    Timber.tag(FLEXI_BANK_LOG_TAG).e(it.stackTraceToString())
}

fun errorLog(message: String) = Timber.tag(FLEXI_BANK_LOG_TAG).e(message)
