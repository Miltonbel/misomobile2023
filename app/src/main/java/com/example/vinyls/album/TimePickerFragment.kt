package com.example.vinyls.album

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Calendar
import java.util.Locale

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(requireActivity(),this,hour,minute,false)
    }


    override fun onTimeSet(view: TimePicker?, selectedHour: Int, selectedMinute: Int) {
        calendar.set(Calendar.MINUTE, selectedMinute)
        calendar.set(Calendar.HOUR, selectedHour)

        val selectedTimeFormat = String.format(Locale.getDefault(),"%02d:%02d", selectedHour, selectedMinute)
        val selectedTimeBundle = Bundle()
        selectedTimeBundle.putString("SELECTED_TIME",selectedTimeFormat)

        setFragmentResult("REQUEST_KEY",selectedTimeBundle)
    }
}