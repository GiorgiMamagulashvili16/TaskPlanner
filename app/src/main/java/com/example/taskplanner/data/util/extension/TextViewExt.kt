package com.example.taskplanner.data.util.extension

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

fun AppCompatTextView.setTextWithMultipleColor(
    text: MutableList<String>,
    colors: MutableList<Int>
) {
    val spannableText = SpannableString(text.joinToString(""))
    var startInd = START_INDEX_FOR_SPANNABLE_TEXT
    (text.indices).forEach {
        spannableText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, colors[it])),
            startInd, text[it].length + startInd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        startInd += text[it].length
    }
    this.text = spannableText
}

private const val START_INDEX_FOR_SPANNABLE_TEXT = 0