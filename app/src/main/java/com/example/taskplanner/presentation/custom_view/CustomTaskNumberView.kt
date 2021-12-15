package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.CustomItemForTaskNumberBinding

class CustomTaskNumberView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defAttrs: Int = 0,
) : ConstraintLayout(context, attrs, defAttrs) {
    private var attributes: TypedArray
    private var roundBackgroundColor: Int
    private var roundBackground: CardView
    private var textView: AppCompatTextView
    private var numberTextView: AppCompatTextView

    init {
        val binding: CustomItemForTaskNumberBinding = CustomItemForTaskNumberBinding.inflate(
            LayoutInflater.from(context), this, true
        )
        attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTaskNumberItem,
            defAttrs,
            0
        )
        with(binding) {
            roundBackground = backgroundView
            textView = statusTextView
        }
        numberTextView = binding.numberTextView

        with(attributes) {
            roundBackgroundColor =
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
        with(textView) {
            text = txt
            setTextColor(textColor)
        }
    }

    private fun setNumberTextParams(numberText: String, numberTextColor: Int) {
        with(numberTextView) {
            text = numberText
            setTextColor(numberTextColor)
        }
    }

    private fun setBackgroundParams(backgroundColor: Int) {
        roundBackground.setCardBackgroundColor(backgroundColor)
    }

    fun setText(newText: String) {
        numberTextView.text = newText
    }
}