package com.example.lab1.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lab1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val tag = "SettingsFragment"

    // ViewModel для настроек
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: SettingsFragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        Log.d(tag, "onCreateView: создаём View фрагмента настроек")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: инициализируем настройки")

        // 1. Подписываемся на LiveData темы
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            Log.d(tag, "observe isDarkTheme: $isDark")

            // Обновляем переключатель только если нужно, чтобы не дергать лишний раз listener
            if (binding.switchDarkMode.isChecked != isDark) {
                binding.switchDarkMode.isChecked = isDark
            }

            // Применяем тему во всём приложении
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // 2. Слушатель переключателя — обновляет ViewModel
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            Log.d(tag, "switchDarkMode changed by user: $isChecked")
            viewModel.setDarkTheme(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(tag, "onDestroyView: View фрагмента настроек уничтожен")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy: SettingsFragment уничтожен")
    }
}
