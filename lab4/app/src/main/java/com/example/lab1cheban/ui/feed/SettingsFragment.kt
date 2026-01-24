package com.example.messenger.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.messenger.R
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"
    private val PREFS_NAME = "settings_prefs"
    private val PREF_NOTIFICATIONS = "notifications_enabled"
    private val PREF_AUTO_SYNC = "auto_sync_enabled"
    private val PREF_FONT_SIZE = "font_size"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: View создан")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: View готов к использованию")

        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val themeSwitch = view.findViewById<SwitchMaterial>(R.id.theme_switch)
        val notificationsSwitch = view.findViewById<SwitchMaterial>(R.id.notifications_switch)
        val autoSyncSwitch = view.findViewById<SwitchMaterial>(R.id.auto_sync_switch)
        val fontSizeSlider = view.findViewById<Slider>(R.id.font_size_slider)
        val fontSizeLabel = view.findViewById<TextView>(R.id.font_size_label)
        val clearCacheButton = view.findViewById<Button>(R.id.clear_cache_button)

        notificationsSwitch.isChecked = prefs.getBoolean(PREF_NOTIFICATIONS, true)
        autoSyncSwitch.isChecked = prefs.getBoolean(PREF_AUTO_SYNC, true)
        fontSizeSlider.value = prefs.getInt(PREF_FONT_SIZE, 1).toFloat()

        updateFontSizeLabel(fontSizeLabel, fontSizeSlider.value.toInt())

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.d(TAG, "Темная тема включена")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.d(TAG, "Светлая тема включена")
            }
        }

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(PREF_NOTIFICATIONS, isChecked).apply()
            Log.d(TAG, "Уведомления: ${if (isChecked) "включены" else "выключены"}")
            Snackbar.make(
                view,
                if (isChecked) "Уведомления включены" else "Уведомления выключены",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        autoSyncSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(PREF_AUTO_SYNC, isChecked).apply()
            Log.d(TAG, "Автообновление: ${if (isChecked) "включено" else "выключено"}")
            Snackbar.make(
                view,
                if (isChecked) "Автообновление включено" else "Автообновление выключено",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        fontSizeSlider.addOnChangeListener { _, value, _ ->
            val size = value.toInt()
            prefs.edit().putInt(PREF_FONT_SIZE, size).apply()
            updateFontSizeLabel(fontSizeLabel, size)
            Log.d(TAG, "Размер шрифта изменен: $size")
        }

        clearCacheButton.setOnClickListener {
            requireContext().cacheDir.deleteRecursively()
            Snackbar.make(view, "Кэш очищен", Snackbar.LENGTH_SHORT).show()
            Log.d(TAG, "Кэш очищен")
        }
    }

    private fun updateFontSizeLabel(label: TextView, size: Int) {
        label.text = when (size) {
            0 -> "Маленький"
            1 -> "Средний"
            2 -> "Большой"
            else -> "Средний"
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Fragment запущен")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Fragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Fragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Fragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: View уничтожен")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Fragment уничтожен")
    }
}