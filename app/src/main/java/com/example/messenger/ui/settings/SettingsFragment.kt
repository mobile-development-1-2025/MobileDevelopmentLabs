package com.example.messenger.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.messenger.R

class SettingsFragment : Fragment() {
    companion object {
        private const val TAG = "SettingsFragment"
    }

    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var themeSwitch: Switch
    private lateinit var tvThemeStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        themeSwitch = view.findViewById(R.id.switch_theme)
        tvThemeStatus = view.findViewById(R.id.tv_theme_status)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            themeSwitch.isChecked = isDark
            tvThemeStatus.text = if (isDark) "Темная тема" else "Светлая тема"
            Log.d(TAG, "Тема обновлена: ${if (isDark) "темная" else "светлая"}")
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}