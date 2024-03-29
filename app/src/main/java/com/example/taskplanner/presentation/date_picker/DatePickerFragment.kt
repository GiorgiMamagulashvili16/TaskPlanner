package com.example.taskplanner.presentation.date_picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.taskplanner.data.util.Constants
import com.example.taskplanner.data.util.Constants.BUNDLE_REQUEST_KEY
import com.example.taskplanner.data.util.Constants.DATE_FORMATTER_PATTERN
import com.example.taskplanner.data.util.Constants.SELECTED_DATE_STRING_KEY
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(
    private val minimumDate: Long? = null,
    private val maximumDate: Long? = null
) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(requireActivity(), this, year, month, day)
        with(datePickerDialog.datePicker) {
            minimumDate?.let {
                minDate = it
            }
            maximumDate?.let {
                maxDate = it
            }
        }
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        with(calendar) {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectedDateBundle = Bundle().apply {
                putLong(SELECTED_DATE_STRING_KEY, calendar.timeInMillis)
            }

            setFragmentResult(BUNDLE_REQUEST_KEY, selectedDateBundle)
        }
    }
}