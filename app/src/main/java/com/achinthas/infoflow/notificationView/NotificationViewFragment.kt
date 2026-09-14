package com.achinthas.infoflow.notificationView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.AppDatabase
import com.achinthas.infoflow.AppRepository
import com.achinthas.infoflow.R
import kotlinx.coroutines.launch

class NotificationViewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppAdapter

    private lateinit var repository: AppRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.list_recyclerView)

        val database = AppDatabase.getDatabase(requireContext())



        repository = AppRepository(database.appDao())



        adapter = AppAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter


        viewLifecycleOwner.lifecycleScope.launch {

            repository.getAllAppsWithNotifications().collect { apps ->

                val sortedApps = apps.sortedByDescending { appWithNotifications ->
                        appWithNotifications.notifications
                            .maxOfOrNull {it.postedTime } ?: 0L
                }
                adapter.updateData(sortedApps)
            }
        }



    }


}