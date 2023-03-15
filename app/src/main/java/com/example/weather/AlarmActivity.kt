package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.weather.databinding.ActivityAlarmBinding
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.helpers.Consts

class AlarmActivity : AppCompatActivity() {
    lateinit var binding : ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textDescription2.text=this.intent.getStringExtra(Consts.WETDESC)
        binding.btnDismiss.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                this@AlarmActivity.finish()
            }

        })

    }
}