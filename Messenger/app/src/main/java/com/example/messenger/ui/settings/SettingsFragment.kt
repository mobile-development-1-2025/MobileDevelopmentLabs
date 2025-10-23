package com.example.messenger.ui.settings
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentSettingsBinding
import androidx.core.graphics.toColorInt
import androidx.core.content.edit

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val PREFS_NAME = "theme_prefs"
    private val THEME_KEY = "theme_mode"
    private val LANGUAGE_KEY = "language_index"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupThemeSwitch()
        setupLanguageButtons()
    }

    private fun setupThemeSwitch() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDark = prefs.getBoolean(THEME_KEY, false)
        binding.switchTheme.isChecked = isDark
        updateThemeStatus(isDark)

        binding.switchTheme.setOnCheckedChangeListener { _, checked ->
            prefs.edit { putBoolean(THEME_KEY, checked) }
            val mode = if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            updateThemeStatus(checked)
        }
    }

    private fun updateThemeStatus(isDark: Boolean) {
        binding.textThemeStatus.text = if (isDark) "Тема: Темная" else "Тема: Светлая"
    }

    private fun setupLanguageButtons() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val russianBtn: Button = binding.btnRussian
        val englishBtn: Button = binding.btnEnglish
        val langIndex = prefs.getInt(LANGUAGE_KEY, 0)
        highlightLanguage(langIndex, russianBtn, englishBtn)

        russianBtn.setOnClickListener {
            prefs.edit { putInt(LANGUAGE_KEY, 0) }
            highlightLanguage(0, russianBtn, englishBtn)
        }

        englishBtn.setOnClickListener {
            prefs.edit { putInt(LANGUAGE_KEY, 1) }
            highlightLanguage(1, russianBtn, englishBtn)
        }
    }

    private fun highlightLanguage(index: Int, russianBtn: Button, englishBtn: Button) {
        if (index == 0) {
            russianBtn.setBackgroundColor("#03DAC5".toColorInt())
            russianBtn.setTextColor(Color.BLACK)
            englishBtn.setBackgroundColor(Color.WHITE)
            englishBtn.setTextColor(Color.BLACK)
        } else {
            englishBtn.setBackgroundColor("#03DAC5".toColorInt())
            englishBtn.setTextColor(Color.BLACK)
            russianBtn.setBackgroundColor(Color.WHITE)
            russianBtn.setTextColor(Color.BLACK)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
