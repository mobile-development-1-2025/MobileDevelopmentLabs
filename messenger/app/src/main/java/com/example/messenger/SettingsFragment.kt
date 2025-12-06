package com.example.messenger

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val TAG = "LifeCycleLog"
    private lateinit var themeSwitcherImageView: ImageView

    private val viewModel: AppViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "SettingsFragment: onViewCreated() – Разметка создана")

        themeSwitcherImageView = view.findViewById(R.id.iv_theme_switcher)

        viewModel.isDarkMode.observe(viewLifecycleOwner) { isDark ->
            val iconRes = if (isDark) R.drawable.ic_moon else R.drawable.ic_sun
            themeSwitcherImageView.setImageResource(iconRes)

            val typedValue = TypedValue()
            val theme = requireContext().theme
            theme.resolveAttribute(com.google.android.material.R.attr.colorOnBackground, typedValue, true)
            val color = typedValue.data

            themeSwitcherImageView.setColorFilter(color)
        }

        themeSwitcherImageView.setOnClickListener {
            toggleTheme()
        }
    }

    private fun toggleTheme() {
        val currentIsDark = viewModel.isDarkMode.value ?: false
        val newIsDark = !currentIsDark

        viewModel.setDarkMode(newIsDark)

        val mode = if (newIsDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        if (AppCompatDelegate.getDefaultNightMode() != mode) {
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SettingsFragment: onCreate()")
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