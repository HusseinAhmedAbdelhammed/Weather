package com.example.weather.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.fakeentity.AlertDomainEntity
import com.example.weather.R
import com.example.weather.adapters.AlertsAdapter
import com.example.weather.databinding.FragmentAlertBinding
import com.example.weather.databinding.FragmentFavDetailsBinding
import com.example.weather.helpers.Alarm
import com.example.weather.helpers.Consts
import com.example.weather.viewmodels.AlertViewModel
import com.example.weather.viewmodels.SharedPrefViewModel
import com.example.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AlertFragment : Fragment() ,OnDeleteAlert{
lateinit var binding:FragmentAlertBinding
lateinit var alarm:Alarm
var min:Int=0
var hour:Int=0
private val sharedPrefViewModel :SharedPrefViewModel by viewModels()
private val viewModel: AlertViewModel by viewModels()
lateinit var dialog:Dialog
    lateinit var alertsAdapter: AlertsAdapter
    private lateinit var startDateid: EditText
    private lateinit var saveAlertsData: Button
    private lateinit var endDateid: EditText
    private lateinit var alertOptions: RadioGroup
    private var startDate: Calendar = Calendar.getInstance()
    private var endDate: Calendar = Calendar.getInstance()
    private var option = "alarm"
    private var startLongDate: Long = 0
    private var endLongDate: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlertBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAlertList()
        lifecycleScope.launch {
            alertsAdapter= AlertsAdapter(requireContext(),this@AlertFragment)
            viewModel.alertList.collect{
                alertsAdapter.submitList(it)
                binding.alertsRecyclerView.apply {
                    adapter=alertsAdapter
                    layoutManager= LinearLayoutManager(context).apply {
                        orientation= RecyclerView.VERTICAL
                    }
                }
            }
        }
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.time_alert)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        startDateid = dialog.findViewById(R.id.startDateid)
        endDateid = dialog.findViewById(R.id.endDateid)
        alertOptions = dialog.findViewById(R.id.alertOptions)
        saveAlertsData = dialog.findViewById(R.id.saveAlertsData)
        binding.addAlertsFloatingActionButton.setOnClickListener {
            dialog.show()
        }
        startDateid.setOnClickListener {
            showDateTimePicker("startDateEditText")
        }
        endDateid.setOnClickListener {
            showDateTimePicker("endDateEditText")

        }
        alertOptions.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: RadioButton = dialog.findViewById(checkedId)
            option = radioButton.getText().toString()
            if (option == getString(R.string.alarm)) {
                option = "alarm"
            } else {
                option = "notification"
            }
        }
        saveAlertsData.setOnClickListener {
            if(sharedPrefViewModel.getNotification()==Consts.NOT_ON){
                alarm= Alarm(requireActivity())
                alarm.setAlarm(hour, min)
            }
            if (startDateid.text.toString().isEmpty()) {
                startDateid.error = "Field is required"
            } else if (endDateid.text.toString().isEmpty()) {
                endDateid.error = "Field is required"
            } else {
                val userAlerts = AlertDomainEntity(startLongDate, endLongDate, option)
                lifecycleScope.launch {
                    viewModel.addAlertTODB(userAlerts)
                }
                viewModel.getAlertList()
                lifecycleScope.launch {
                    viewModel.alertList.collect{
                        alertsAdapter.submitList(it)

                    }
                }
                startDateid.setText("")
                endDateid.setText("")
                dialog.cancel()
            }
        }



    }
    private fun showDateTimePicker(label: String) {
        val currentDate = Calendar.getInstance()
        var date: Calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)
                TimePickerDialog(
                    context,
                    { view, hourOfDay, minute ->
                        hour=hourOfDay
                        min=minute
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        if (label == "startDateEditText") {
                            startDate = date
                            startLongDate = updateLabel(startDate, startDateid)
                        } else {
                            endDate = date
                            endLongDate = updateLabel(endDate, endDateid)
                        }

                    }, currentDate[Calendar.HOUR_OF_DAY], currentDate[Calendar.MINUTE], false
                ).show()
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH], currentDate[Calendar.DATE]
        )
        datePickerDialog.datePicker.minDate = currentDate.timeInMillis;
        datePickerDialog.show();
    }

    private fun updateLabel(calendar: Calendar, editText: EditText): Long {
        val myFormat = "HH:mm a\ndd/MM/yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale("en"))
        val milliseconds: Long = calendar.timeInMillis

        editText.setText(dateFormat.format(calendar.time))
        return milliseconds
    }

    override fun delete(alert: AlertDomainEntity) {
        viewModel.delAlertFromDB(alert)

    }
}