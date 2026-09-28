package com.achinthas.infoflow.settingsView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.achinthas.infoflow.R
import com.google.android.material.materialswitch.MaterialSwitch

class AppsListAdapter(private val installedApps : List<InstalledApps>,
            private val context: Context) :
    RecyclerView.Adapter<AppsListAdapter.AppListHolder>() {

        private val prefs = context.getSharedPreferences("app_data", Context.MODE_PRIVATE)
        private val selectedPackages =  mutableSetOf<String>().apply {
            addAll(
                prefs.getStringSet("notification_filter_apps",emptySet() ) ?: emptySet()
            )
        }


    class AppListHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val img_app_icon : ImageView = itemView.findViewById(R.id.img_app_icon)
        val txt_app_name: TextView = itemView.findViewById(R.id.txt_app_name)
        val switch_enable : MaterialSwitch = itemView.findViewById(R.id.switch_enable)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noti_filter_installed_app, parent, false)

        return AppListHolder(view)
    }

    override fun onBindViewHolder(
        holder: AppListHolder,
        position: Int
    ) {
        val app = installedApps[position]

        holder.txt_app_name.text = app.appName
        holder.img_app_icon.setImageDrawable(app.icon)

        // select apps to blocking listener
        holder.switch_enable.setOnCheckedChangeListener(null)
        holder.switch_enable.isChecked = selectedPackages.contains(app.packageName)

        holder.switch_enable.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                selectedPackages.add(app.packageName)
            }else{
                selectedPackages.remove(app.packageName)
            }

            prefs.edit()
                .putStringSet(
                    "notification_filter_apps",
                    selectedPackages
                )
                .apply()
        }
    }

    override fun getItemCount(): Int {
       return installedApps.size
    }


}