package com.achinthas.infoflow.onboarding

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.achinthas.infoflow.R


class BatteryOptimizationPermFragment : Fragment(R.layout.fragment_battery_optimization_perm) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val batteryOptPerm = view.findViewById<Button>(R.id.btnGrantBatteryOpt)

        batteryOptPerm.setOnClickListener {

            val intent = Intent().apply {
                action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                data = Uri.parse("package:com.achinthas.infoflow")
            }
            startActivity(intent)

        }

    }


}