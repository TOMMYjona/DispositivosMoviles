package com.example.calendariol

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.time.LocalTime
import java.util.*
import java.util.Calendar.MINUTE

@RequiresApi(Build.VERSION_CODES.O)
class TimePickerFragment : DialogFragment() {

    private var listener: TimePickerDialog.OnTimeSetListener? = null
    /*private var hour: Int
    private var minute: Int*/

    /*init {
        val now = LocalTime.now()
        hour = now.hour
        minute = now.minute
    }*/

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val t = Calendar.getInstance()
        val hour = t.get(Calendar.HOUR)
        val minute = t.get(Calendar.MINUTE)

        return TimePickerDialog(requireActivity(), listener, hour, minute, false)
    }

    companion object {
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickerFragment {

            return TimePickerFragment().apply {
                val fragment = TimePickerFragment()
                fragment.listener = listener
                return fragment
            }
        }
    }
}