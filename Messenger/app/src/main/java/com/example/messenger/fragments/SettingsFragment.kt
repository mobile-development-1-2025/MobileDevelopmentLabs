package com.example.messenger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.messenger.R
import com.example.messenger.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    companion object {
        private const val TAG = "SettingsFragment"
    }

    private val viewModel: SettingsViewModel by activityViewModels()
    private lateinit var themeSwitch: Switch
    private var isUpdatingFromViewModel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView called")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")
        
        themeSwitch = view.findViewById(R.id.theme_switch)
        setupThemeSwitch()
    }

    private fun setupThemeSwitch() {
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDarkTheme ->
            if (!isUpdatingFromViewModel && themeSwitch.isChecked != isDarkTheme) {
                isUpdatingFromViewModel = true
                themeSwitch.isChecked = isDarkTheme
                isUpdatingFromViewModel = false
            }
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isUpdatingFromViewModel) {
                Log.d(TAG, "Theme switch changed: $isChecked")
                viewModel.setDarkTheme(isChecked)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}
