package com.achinthas.infoflow.settingsView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.achinthas.infoflow.AppDatabase
import com.achinthas.infoflow.AppRepository
import com.achinthas.infoflow.NotificationRepository
import com.achinthas.infoflow.R
import kotlinx.coroutines.launch
import java.io.File

class SettingsViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(com.achinthas.infoflow.R.id.toolbar)

        toolbar.title = "Settings"

        val blockNotificationSetting = view.findViewById<LinearLayout>(R.id.blockNotificationSetting)

        val storageUsageSetting = view.findViewById<LinearLayout>(R.id.storageUsageSetting)

        val deleteAllDataSetting = view.findViewById<LinearLayout>(R.id.deleteAllDataSetting)

        val privacyPolicySetting = view.findViewById<LinearLayout>(R.id.privacyPolicySetting)

        val termsConditionsSetting = view.findViewById<LinearLayout>(R.id.termsConditionsSetting)

        val aboutSetting = view.findViewById<LinearLayout>(R.id.aboutSetting)

        blockNotificationSetting.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, NotificationFilterFragment())
                .addToBackStack(null)
                .commit()
        }

        storageUsageSetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, StorageUsageFragment())
                .addToBackStack(null)
                .commit()
        }

        deleteAllDataSetting.setOnClickListener {
//            deleteAllData(())

            showDeleteConfirmationDialog()
        }

        privacyPolicySetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, PrivacyPolicySettingFragment())
                .addToBackStack(null)
                .commit()
        }

        termsConditionsSetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, TermsConditionsSettingFragment())
                .addToBackStack(null)
                .commit()
        }

        aboutSetting.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, AboutSettingFragment())
                .addToBackStack(null)
                .commit()
        }


    }

    // delete all data
    fun deleteAllData(context: Context) {

        viewLifecycleOwner.lifecycleScope.launch {
            val database = AppDatabase.getDatabase(context)

            val notificationRepo = NotificationRepository(database.notificationDao())
            val appRepo = AppRepository(database.appDao())

            notificationRepo.deleteAllNotifications()
            appRepo.deleteAllApps()

            context.getExternalFilesDir(null)?.deleteRecursively()
        }

    }

    // dialog box confirmation
    private fun showDeleteConfirmationDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Delete all data?")
            .setMessage(
                "This will permanently delete all saved notifications, app data, " +
                        "and notification images. This action cannot be undone."
            )
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                deleteAllData(requireContext())
            }
            .show()
    }






}