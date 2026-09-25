package com.achinthas.infoflow.settingsView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        val btnContactUs = view.findViewById<LinearLayout>(R.id.btnContactUs)

        val expandableLayout = view.findViewById<LinearLayout>(R.id.layoutExpandableContent)

        val contactUsChevronRight = view.findViewById<ImageView>(R.id.contactUsChevronRight)

        val btnSendEmail = view.findViewById<LinearLayout>(R.id.btnSendEmail)

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

        btnContactUs.setOnClickListener {

            val isExpanded = expandableLayout.visibility == View.VISIBLE


            if (isExpanded) {
                expandableLayout.visibility = View.GONE
                contactUsChevronRight.animate().rotation(0f).setDuration(200).start()
            } else {
                expandableLayout.visibility = View.VISIBLE
                contactUsChevronRight.animate().rotation(90f).setDuration(200).start()
            }

        }

        btnSendEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply{
                data = Uri.parse("mailto:achinthash@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Notification Manager Feedback")
            }
            startActivity(Intent.createChooser(intent, "Send Email"))
        }

    }
}