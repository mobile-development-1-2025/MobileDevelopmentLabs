package com.m.labs_dk_lab1.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.m.labs_dk_lab1.databinding.FragmentSettingsBinding
import com.m.labs_dk_lab1.util.Logger
import com.m.labs_dk_lab1.viewmodel.AppViewModel

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: AppViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Logger.log("SettingsFragment view created")
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Logger.log("SettingsFragment view ready")

        viewModel.isDarkTheme.observe(viewLifecycleOwner) {
            binding.themeSwitch.isChecked = it
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            viewModel.setTheme(checked)
        }
    }
}
