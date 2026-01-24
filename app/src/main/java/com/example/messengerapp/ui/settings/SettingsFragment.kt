package com.example.messenger.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger.databinding.FragmentSettingsBinding
import com.example.messengerapp.ui.AppViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val appVM: AppViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SettingsFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.updatePadding(top = top + v.paddingTop)
            insets
        }

        appVM.isDark.observe(viewLifecycleOwner) { isDark ->
            if (binding.themeSwitch.isChecked != isDark) {
                binding.themeSwitch.isChecked = isDark
            }
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            appVM.setDarkMode(checked)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
