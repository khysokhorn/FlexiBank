package com.nexgen.flexiBank.module.view.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nexgen.flexiBank.R
import com.nexgen.flexiBank.model.Language

class LanguageAdapter(private val context: Context, private val languageList: List<Language>) : ArrayAdapter<Language>(context, 0, languageList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.select_language, parent, false)
        val item = getItem(position)

        val icon = view.findViewById<ImageView>(R.id.flagIcon)
        val name = view.findViewById<TextView>(R.id.languageName)

        item?.let {
            icon.setImageResource(it.flagResId)
            name.text = it.name
        }

        return view
    }
}