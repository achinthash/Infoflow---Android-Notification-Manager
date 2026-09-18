package com.achinthas.infoflow.settingsView

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.R
import com.achinthas.infoflow.notificationView.NotificationAdapter
import kotlinx.coroutines.launch


class NotificationFilterFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_filter, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar : Toolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.installedAppsRecyclerView)

        viewLifecycleOwner.lifecycleScope.launch {

            val installedApps  = getInstalledApps(requireContext())

            adapter = AppsListAdapter(installedApps, requireContext())

            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            recyclerView.adapter = adapter

        }
    }


    //fun for get all installed apps
    fun getInstalledApps(context: Context): List<InstalledApps> {
        val packageManager = context.packageManager
        val appList = mutableListOf<InstalledApps>()

        // Query intent matching the <queries> section in your Manifest
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        // Retrieve all activities that match the launcher intent
        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)

        for (resolveInfo in resolveInfoList) {
            val packageName = resolveInfo.activityInfo.packageName

            // Exclude your own app from the filter list if desired
            if (packageName == context.packageName) continue

            val appName = resolveInfo.loadLabel(packageManager).toString()
            val icon = resolveInfo.loadIcon(packageManager)

            appList.add(InstalledApps(appName, packageName, icon))
        }

        // Remove potential duplicate entries and sort alphabetically
        return appList.distinctBy { it.packageName }.sortedBy { it.appName.lowercase() }
    }
}