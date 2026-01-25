package com.example.mymessenger

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mymessenger.viewmodel.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"
    private val viewModel: SettingsViewModel by viewModels()

    private var isUpdatingFromVm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switchDarkTheme)

        // VM -> UI
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            Log.d(TAG, "Theme observed: $isDark")

            if (switchTheme.isChecked != isDark) {
                isUpdatingFromVm = true
                switchTheme.isChecked = isDark
                isUpdatingFromVm = false
            }

            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // UI -> VM
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isUpdatingFromVm) return@setOnCheckedChangeListener
            Log.d(TAG, "Switch toggled: $isChecked")
            viewModel.toggleTheme(isChecked)
        }
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }
}
