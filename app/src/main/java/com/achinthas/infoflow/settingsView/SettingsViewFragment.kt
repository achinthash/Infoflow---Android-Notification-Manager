package com.achinthas.infoflow.settingsView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
            deleteAllData(requireContext())
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






}