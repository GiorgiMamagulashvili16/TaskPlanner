package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.CustomTextInputLayoutBinding
import com.google.android.material.textfield.TextInputEditText

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defAttrs: Int = 0
) : ConstraintLayout(context, attrs, defAttrs) {
    val binding: CustomTextInputLayoutBinding =
        CustomTextInputLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    var editText: TextInputEditText

    init {
        val attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextInputLayout,
            defAttrs,
            0
        )
        editText = binding.rootEditText
        with(attributes) {
            val inputType =
                getInt(R.styleable.CustomTextInputLayout_android_inputType, EditorInfo.TYPE_NULL)
            val maxLines =
                getInt(R.styleable.CustomTextInputLayout_android_maxLines, DEFAULT_LINE_NUMBER)
            val hintText = getString(R.styleable.CustomTextInputLayout_android_hint).toString()
            setEditTextParams(maxLines, inputType)
            setTextInputLayoutParams(hintText)
        }
    }

    private fun setEditTextParams(lines: Int = DEFAULT_LINE_NUMBER, editTextInputType: Int) {
        with(binding.rootEditText) {
            maxLines = lines
            inputType = editTextInputType
        }
    }

    private fun setTextInputLayoutParams(hintText: String) {
        with(binding.rootLayout) {
            hint = hintText
        }
    }

    companion object {
        private const val DEFAULT_LINE_NUMBER = 1
    }
}