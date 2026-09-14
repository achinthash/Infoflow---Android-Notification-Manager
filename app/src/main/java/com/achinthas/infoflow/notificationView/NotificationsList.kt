package com.achinthas.infoflow.notificationView

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.AppDatabase
import com.achinthas.infoflow.AppRepository
import com.achinthas.infoflow.NotificationDao
import com.achinthas.infoflow.NotificationRepository
import kotlinx.coroutines.launch

class NotificationsList: AppCompatActivity() {

    private lateinit var repository: NotificationRepository

    private lateinit var adapter: NotificationAdapter

    private lateinit var recyclerView: RecyclerView


    private lateinit var toolbarDeleteBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.achinthas.infoflow.R.layout.activity_notifications_list)

        setupToolbar()
        setupRecyclerView()
        loadNotifications()
    }


    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar>(com.achinthas.infoflow.R.id.toolbar)
        val toolbarTitle = findViewById<TextView>(com.achinthas.infoflow.R.id.toolbar_title)
        val toolbarImg = findViewById<ImageView>(com.achinthas.infoflow.R.id.toolbar_img)

        toolbarDeleteBtn = findViewById<ImageButton>(com.achinthas.infoflow.R.id.toolbar_deleteButton)

        toolbarDeleteBtn.visibility = View.GONE


//        val searchView = findViewById<androidx.appcompat.widget.SearchView>(com.achinthas.infoflow.R.id.searchView)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
        }

        toolbar.navigationIcon?.setTint(Color.WHITE)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val packageName = intent.getStringExtra("EXTRA_PACKAGE_NAME")
        val appName = intent.getStringExtra("EXTRA_APP_NAME")

        toolbarTitle.text = appName ?: "Notifications"

        if (!packageName.isNullOrEmpty()) {
            try {
                val icon = packageManager.getApplicationIcon(packageName)
                toolbarImg.setImageDrawable(icon)
            } catch (e: PackageManager.NameNotFoundException) {
                toolbarImg.setImageResource(com.achinthas.infoflow.R.drawable.baseline_android_24)
            }
        } else {
            toolbarImg.setImageResource(com.achinthas.infoflow.R.drawable.baseline_android_24)
        }

        toolbarDeleteBtn.setOnClickListener {

            val selectedIds = adapter.getSelectedIds()

            if(selectedIds.isEmpty()){
                return@setOnClickListener
            }

            lifecycleScope.launch {
                repository.deleteNotificationsByIds(selectedIds.toSet())
                adapter.clearSelection()
            }
        }

//        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Trigger filter on keyboard submit
//                adapter.filter(query.orEmpty())
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Trigger filter continuously as the user types
//                adapter.filter(newText.orEmpty())
//                return true
//            }
//        })



    }

     override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.achinthas.infoflow.R.menu.search_menu, menu)

        val searchItem = menu.findItem(com.achinthas.infoflow.R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    private fun setupRecyclerView() {
        recyclerView = findViewById(com.achinthas.infoflow.R.id.notificationListRecyclerView)


        val database = AppDatabase.getDatabase(this)

        repository = NotificationRepository(
            database.notificationDao()
        )

        adapter = NotificationAdapter(emptyList(),repository){ selectedCount ->


           toolbarDeleteBtn.visibility = if(selectedCount > 0){
               View.VISIBLE
               } else{
               View.GONE
               }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotificationsList)
            adapter = this@NotificationsList.adapter
            setHasFixedSize(true)
        }


    }

    private fun loadNotifications() {

        val appId = intent.getLongExtra("EXTRA_APP_ID", -1L)

        if (appId == -1L) {
            return
        }


        lifecycleScope.launch {

            repository.getNotificationsByApp(appId).collect { notifications ->
                adapter.updateData(notifications)
            }
        }
    }


}