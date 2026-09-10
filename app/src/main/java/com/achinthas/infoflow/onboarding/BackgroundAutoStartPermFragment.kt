package com.achinthas.infoflow.onboarding

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.achinthas.infoflow.R

class BackgroundAutoStartPermFragment : Fragment(R.layout.fragment_background_auto_start_perm) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val autoStartPerm = view.findViewById<Button>(R.id.btnGrantAutoStart)

        autoStartPerm.setOnClickListener {

            val manufacture = Build.MANUFACTURER.lowercase()

            val intent = Intent()

            when (manufacture) {

                "xiaomi" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity"
                        )
                    )
                }
                "oppo" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                        )
                    )
                }
                "vivo" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.vivo.permissionmanager",
                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                        )
                    )
                }
                "huawei" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.huawei.systemmanager",
                            "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                        )
                    )
                }

                "huawei" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.hihonor.systemmanager",
                            "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                        )
                    )
                }
                "asus" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.asus.mobilemanager",
                            "com.asus.mobilemanager.entry.FunctionActivity"
                        )
                    )
                }
                "samsung" -> {
                    intent.setComponent(
                        ComponentName(
                            "com.samsung.android.lool",
                            "com.samsung.android.sm.ui.battery.BatteryActivity"
                        )
                    )
                }
                else -> {
                    null
                }
            }

            startActivity(intent)


        }

    }

}