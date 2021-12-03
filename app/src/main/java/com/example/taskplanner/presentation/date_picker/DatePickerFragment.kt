package com.example.taskplanner.presentation.date_picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.taskplanner.data.util.Constants.BUNDLE_REQUEST_KEY
import com.example.taskplanner.data.util.Constants.DATE_FORMATTER_PATTERN
import com.example.taskplanner.data.util.Constants.SELECTED_DATE_STRING_KEY
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        with(calendar) {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val selectedTime =
                SimpleDateFormat(DATE_FORMATTER_PATTERN, Locale.getDefault()).format(calendar.time)
            val selectedDateBundle = Bundle().apply {
                putString(SELECTED_DATE_STRING_KEY, selectedTime)
            }

            setFragmentResult(BUNDLE_REQUEST_KEY, selectedDateBundle)
        }
    }
}