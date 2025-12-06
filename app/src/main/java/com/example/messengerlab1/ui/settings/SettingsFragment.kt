package com.example.messengerlab1.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messengerlab1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsScreenViewModel by viewModels()
    private val tagLog = "SettingsFragmentV2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagLog, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        Log.d(tagLog, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tagLog, "onViewCreated")

        viewModel.isNightMode.observe(viewLifecycleOwner) { enabled ->
            Log.d(tagLog, "observe isNightMode = $enabled")

            if (binding.switchTheme.isChecked != enabled) {
                binding.switchTheme.isChecked = enabled
            }

            AppCompatDelegate.setDefaultNightMode(
                if (enabled) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            Log.d(tagLog, "switchTheme changed: $isChecked")
            viewModel.setNightMode(isChecked)
        }
    }

    override fun onStart()  { super.onStart();  Log.d(tagLog, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tagLog, "onResume") }
    override fun onPause()  { super.onPause();  Log.d(tagLog, "onPause") }
    override fun onStop()   { super.onStop();   Log.d(tagLog, "onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagLog, "onDestroyView")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tagLog, "onDestroy")
    }
}
