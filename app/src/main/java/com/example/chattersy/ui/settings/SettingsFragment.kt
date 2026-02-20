package com.example.chattersy.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chattersy.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment()
{
    companion object
    {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var themeSwitch: SwitchMaterial
    private lateinit var offlineSwitch: SwitchMaterial
    private lateinit var openPermissionSettingsButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        Log.d(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        val factory = object : AbstractSavedStateViewModelFactory(this, arguments) {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                    return SettingsViewModel(requireActivity().application, handle) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        viewModel = ViewModelProvider(this, factory)[SettingsViewModel::class.java]

        themeSwitch = view.findViewById(R.id.themeSwitch)
        offlineSwitch = view.findViewById(R.id.offlineSwitch)
        openPermissionSettingsButton = view.findViewById(R.id.openPermissionSettingsButton)

        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            themeSwitch.isChecked = isDark
            applyTheme(isDark)
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkTheme(isChecked)
            applyTheme(isChecked)
        }

        viewModel.forceOffline.observe(viewLifecycleOwner) { isOffline ->
            offlineSwitch.isChecked = isOffline
        }

        offlineSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setForceOffline(isChecked)
        }

        openPermissionSettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${requireContext().packageName}")
            }
            startActivity(intent)
        }
    }

    private fun applyTheme(isDark: Boolean)
    {
        val mode = if (isDark)
        {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        else
        {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}
