package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.CustomItemForTaskNumberBinding

class CustomTaskNumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defAttrs: Int = 0,
) : ConstraintLayout(context, attrs, defAttrs) {
    private var binding: CustomItemForTaskNumberBinding = CustomItemForTaskNumberBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTaskNumberItem,
            defAttrs,
            0
        )
        with(attributes) {
            val roundBackgroundColor =
                getInt(R.styleable.CustomTaskNumberItem_roundBackgroundColor, Color.RED)
            val text = getString(R.styleable.CustomTaskNumberItem_text).toString()
            val textColor = getInt(R.styleable.CustomTaskNumberItem_textColor, Color.BLACK)
            val numberColor = getInt(R.styleable.CustomTaskNumberItem_numberColor, Color.WHITE)
            val numberText = getString(R.styleable.CustomTaskNumberItem_numberText).toString()
            setTextParams(text, textColor)
            setNumberTextParams(numberText, numberColor)
            setBackgroundParams(roundBackgroundColor)
        }
    }

    private fun setTextParams(txt: String, textColor: Int) {
        with(binding.statusTextView) {
            text = txt
            setTextColor(textColor)
        }
    }

    private fun setNumberTextParams(numberText: String, numberTextColor: Int) {
        with(binding.numberTextView) {
            text = numberText
            setTextColor(numberTextColor)
        }
    }

    private fun setBackgroundParams(backgroundColor: Int) {
        binding.backgroundView.setCardBackgroundColor(backgroundColor)
    }

    fun setText(newText: String) {
        binding.numberTextView.text = newText
    }
}