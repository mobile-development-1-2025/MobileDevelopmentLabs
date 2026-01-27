package com.example.messenger.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messenger.databinding.FragmentSettingsBinding
import com.example.messenger.viewmodel.SettingsViewModel

/**
 * Экран "Настройки" с использованием MVVM архитектуры
 * Состояние темы хранится во ViewModel и переживает повороты экрана
 */
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SettingsFragment"
    
    // ViewModel с использованием делегата viewModels()
    private val viewModel: SettingsViewModel by viewModels()
    
    // Флаг для предотвращения срабатывания listener при программной установке
    private var isUpdatingFromViewModel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: SettingsFragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: SettingsFragment создание View")
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: SettingsFragment View создан")
        
        // Инициализация ViewModel из SharedPreferences при первом создании
        initViewModelFromPrefs()
        
        setupObservers()
        setupListeners()
    }
    
    /**
     * Инициализация начального состояния ViewModel из SharedPreferences
     */
    private fun initViewModelFromPrefs() {
        val sharedPrefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val savedDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        viewModel.initTheme(savedDarkMode)
        Log.d(TAG, "initViewModelFromPrefs: Загружено состояние темы - ${if (savedDarkMode) "Тёмная" else "Светлая"}")
    }
    
    /**
     * Настройка наблюдателей LiveData
     * UI реактивно обновляется при изменении состояния во ViewModel
     */
    private fun setupObservers() {
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            Log.d(TAG, "Observer: Получено новое состояние темы - ${if (isDark) "Тёмная" else "Светлая"}")
            
            // Устанавливаем флаг, чтобы listener не срабатывал
            isUpdatingFromViewModel = true
            binding.switchTheme.isChecked = isDark
            isUpdatingFromViewModel = false
        }
    }
    
    /**
     * Настройка слушателя переключателя темы
     */
    private fun setupListeners() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Игнорируем, если изменение пришло из ViewModel
            if (isUpdatingFromViewModel) return@setOnCheckedChangeListener
            
            Log.d(TAG, "Переключение темы пользователем: ${if (isChecked) "Тёмная" else "Светлая"}")
            
            // Обновляем ViewModel
            viewModel.setDarkTheme(isChecked)
            
            // Сохраняем в SharedPreferences
            val sharedPrefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
            
            // Применяем тему
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: SettingsFragment стартовал")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: SettingsFragment возобновлен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: SettingsFragment приостановлен")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: SettingsFragment остановлен")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: SettingsFragment View уничтожен")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: SettingsFragment уничтожен")
    }
}
