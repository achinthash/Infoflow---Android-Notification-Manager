package com.achinthas.infoflow.settingsView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import com.achinthas.infoflow.R


class AboutSettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPrivacyPolicy = view.findViewById<LinearLayout>(R.id.btnPrivacyPolicy)

        val btnTermsAndConditions = view.findViewById<LinearLayout>(R.id.btnTermsAndConditions)

        val toolbar : Toolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnPrivacyPolicy.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, PrivacyPolicySettingFragment())
                .addToBackStack(null)
                .commit()
        }

        btnTermsAndConditions.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, TermsConditionsSettingFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}