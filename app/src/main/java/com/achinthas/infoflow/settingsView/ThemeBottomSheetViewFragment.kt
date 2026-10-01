package com.achinthas.infoflow.settingsView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import com.achinthas.infoflow.R
import com.achinthas.infoflow.ThemeManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ThemeBottomSheetViewFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theme_bottom_sheet_view, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ThemeManager.applySaveTheme(requireContext())

        val themeRadioGroup = view.findViewById<RadioGroup>(R.id.themeRadioGroup)

        // Pre-select the option matching the saved setting
        when (ThemeManager.getSavedTheme(requireContext())) {
            ThemeManager.THEME_LIGHT -> themeRadioGroup.check(R.id.radioLight)
            ThemeManager.THEME_DARK -> themeRadioGroup.check(R.id.radioDark)
            else -> themeRadioGroup.check(R.id.radioSystem)
        }

        themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedMode = when(checkedId) {
                R.id.radioLight -> {
                    ThemeManager.THEME_LIGHT
                }
                R.id.radioDark -> {
                    ThemeManager.THEME_DARK
                }
                else ->{
                    ThemeManager.THEME_SYSTEM
                }
            }

            ThemeManager.saveTheme(requireContext(), selectedMode)
        }
    }

}