package com.example.messenger.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.messenger.R
import com.example.messenger.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    companion object {
        private const val TAG = "SettingsFragment"
        private const val PREFS_NAME = "messenger_prefs"
        private const val KEY_THEME = "theme_mode"
    }
    
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
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        
        binding.textSettingsContent.text = getString(R.string.settings_content)
        
        setupThemeSwitch()
        setupNotificationsSwitch()
    }
    
    private fun setupThemeSwitch() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val currentTheme = prefs.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        
        binding.switchTheme.isChecked = currentTheme == AppCompatDelegate.MODE_NIGHT_YES
        
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            
            AppCompatDelegate.setDefaultNightMode(newTheme)
            prefs.edit().putInt(KEY_THEME, newTheme).apply()
            
            Log.d(TAG, "Theme changed to: ${if (isChecked) "Dark" else "Light"}")
        }
    }
    
    private fun setupNotificationsSwitch() {
        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val message = if (isChecked) {
                getString(R.string.notifications_enabled)
            } else {
                getString(R.string.notifications_disabled)
            }
            
            binding.textNotificationsStatus.text = message
            Log.d(TAG, "Notifications ${if (isChecked) "enabled" else "disabled"}")
        }
    }
    
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }
    
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }
    
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }
    
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
        _binding = null
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}


