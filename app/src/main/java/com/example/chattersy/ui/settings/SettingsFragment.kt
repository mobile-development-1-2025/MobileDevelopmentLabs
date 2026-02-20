package com.example.chattersy.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.chattersy.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment()
{
    companion object
    {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var themeSwitch: SwitchMaterial

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

        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        themeSwitch = view.findViewById(R.id.themeSwitch)

        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            themeSwitch.isChecked = isDark
            applyTheme(isDark)
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkTheme(isChecked)
            applyTheme(isChecked)
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
