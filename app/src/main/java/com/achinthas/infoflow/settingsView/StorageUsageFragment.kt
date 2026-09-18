package com.achinthas.infoflow.settingsView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.achinthas.infoflow.AppDatabase
import com.achinthas.infoflow.R
import java.io.File
import java.util.Locale

class StorageUsageFragment : Fragment() {

    private lateinit var totalStorageText: TextView
    private lateinit var databaseSizeText: TextView
    private lateinit var notificationFilesSizeText: TextView
    private lateinit var appFilesSizeText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_storage_usage,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar : Toolbar = view.findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }


        totalStorageText = view.findViewById(R.id.total_storage_text)
        databaseSizeText = view.findViewById(R.id.database_size_text)
        notificationFilesSizeText =
            view.findViewById(R.id.notification_files_size_text)
        appFilesSizeText = view.findViewById(R.id.app_files_size_text)

        loadStorageData()
    }

    private fun loadStorageData() {

        val storage = getStorageSize(requireContext())

        totalStorageText.text = formatSize(storage.total)
        databaseSizeText.text = formatSize(storage.database)
        notificationFilesSizeText.text = formatSize(storage.files)
        appFilesSizeText.text = formatSize(storage.internal)
    }

    private fun getDirectorySize(directory: File): Long {

        if (!directory.exists()) {
            return 0L
        }

        return directory.walkTopDown()
            .filter { it.isFile }
            .sumOf { it.length() }
    }

    private fun getStorageSize(context: Context): StorageSize {

        val databaseFile =
            context.getDatabasePath(AppDatabase.DATABASE_NAME)

        val databaseSize = if (databaseFile.exists()) {
            databaseFile.length()
        } else {
            0L
        }

        val notificationFilesSize =
            context.getExternalFilesDir(null)?.let {
                getDirectorySize(it)
            } ?: 0L

        val appFilesSize =
            getDirectorySize(context.filesDir)

        val totalSize =
            databaseSize +
                    notificationFilesSize +
                    appFilesSize

        return StorageSize(
            database = databaseSize,
            files = notificationFilesSize,
            internal = appFilesSize,
            total = totalSize
        )
    }

    private fun formatSize(bytes: Long): String {

        val mb = bytes / (1024.0 * 1024.0)

        return when {
            mb < 0.01 -> {
                String.format(
                    Locale.getDefault(),
                    "%.1f KB",
                    bytes / 1024.0
                )
            }

            mb < 1024 -> {
                String.format(
                    Locale.getDefault(),
                    "%.2f MB",
                    mb
                )
            }

            else -> {
                String.format(
                    Locale.getDefault(),
                    "%.2f GB",
                    mb / 1024.0
                )
            }
        }
    }

    data class StorageSize(
        val database: Long,
        val files: Long,
        val internal: Long,
        val total: Long
    )
}