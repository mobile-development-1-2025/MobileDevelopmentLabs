package com.mobile.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import com.mobile.lab1.databinding.FragmentSettingsBinding
import com.mobile.lab1.viewmodel.SettingsViewModel

class SettingsFragment : LoggingFragment("SettingsFragment") {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by activityViewModels()

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

        val listener = { _: CompoundButton, isChecked: Boolean ->
            viewModel.setDarkTheme(isChecked)
        }

        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            binding.switchTheme.setOnCheckedChangeListener(null)
            binding.switchTheme.isChecked = isDark
            binding.switchTheme.setOnCheckedChangeListener(listener)

            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding.switchTheme.setOnCheckedChangeListener(listener)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}