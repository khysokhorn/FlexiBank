package com.nexgen.flexiBank.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class AmountVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Keep original text and mapping, but allows us to control appearance in the future
        // This can be extended to format with commas or other currency formatting if needed
        return TransformedText(text, OffsetMapping.Identity)
    }
}
