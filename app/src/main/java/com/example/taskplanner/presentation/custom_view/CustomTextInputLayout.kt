package com.example.taskplanner.presentation.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.taskplanner.R
import com.example.taskplanner.databinding.CustomTextInputLayoutBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defAttrs: Int = 0
) : ConstraintLayout(context, attrs, defAttrs) {
    var textInputLayout: TextInputLayout
    var editText: TextInputEditText
    private var attributes: TypedArray
    private var inputType: Int
    private var maxLines: Int
    private var hintText: String


    init {
        val binding: CustomTextInputLayoutBinding =
            CustomTextInputLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        attributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomTextInputLayout,
            defAttrs,
            0
        )
        textInputLayout = binding.rootLayout
        editText = binding.rootEditText
        with(attributes) {
            inputType =
                getInt(R.styleable.CustomTextInputLayout_android_inputType, EditorInfo.TYPE_NULL)
            maxLines = getInt(R.styleable.CustomTextInputLayout_android_maxLines, 1)
            hintText = getString(R.styleable.CustomTextInputLayout_android_hint).toString()
        }
        setEditTextParams(maxLines, inputType)
        setTextInputLayoutParams(hintText)
    }

    private fun setEditTextParams(lines: Int = DEFAULT_LINE_NUMBER, editTextInputType: Int) {
        with(editText) {
            maxLines = lines
            inputType = editTextInputType
        }
    }

    private fun setTextInputLayoutParams(hintText: String) {
        with(textInputLayout) {
            hint = hintText
        }
    }

    companion object {
        private const val DEFAULT_LINE_NUMBER = 1
    }
}