package com.example.messenger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val TAG = "LifeCycleLog"
    private lateinit var themeSwitcherImageView: ImageView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "SettingsFragment: onAttach() – Присоединение к Activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SettingsFragment: onCreate()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "SettingsFragment: onViewCreated() – Разметка создана")

        themeSwitcherImageView = view.findViewById(R.id.iv_theme_switcher)
        updateThemeSwitcherIcon()
        themeSwitcherImageView.setOnClickListener {
            toggleTheme()
        }
    }

    private fun toggleTheme() {
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        val newNightMode = if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.MODE_NIGHT_NO
        } else {
            AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(newNightMode)
        activity?.recreate()
    }

    private fun updateThemeSwitcherIcon() {
        val isNightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (isNightMode) {
            themeSwitcherImageView.setImageResource(R.drawable.ic_sun)
        } else {
            themeSwitcherImageView.setImageResource(R.drawable.ic_moon)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "SettingsFragment: onStart() – Фрагмент виден")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "SettingsFragment: onResume() – Фрагмент активен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "SettingsFragment: onPause() – Фрагмент теряет фокус")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "SettingsFragment: onStop() – Фрагмент не виден")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "SettingsFragment: onDestroyView() – Разметка уничтожена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SettingsFragment: onDestroy() – Фрагмент уничтожен")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "SettingsFragment: onDetach() – Отсоединение от Activity")
    }
}