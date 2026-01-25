package ru.itmo.mobiledev.lab4

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        val themeSwitch = view.findViewById<SwitchMaterial>(R.id.theme_switch)
        val offlineSwitch = view.findViewById<SwitchMaterial>(R.id.offline_switch)
        val isNight = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        viewModel.setDarkThemeEnabled(isNight)

        viewModel.darkThemeEnabled.observe(viewLifecycleOwner) { enabled ->
            if (themeSwitch.isChecked != enabled) {
                themeSwitch.isChecked = enabled
            }
        }

        viewModel.offlineEnabled.observe(viewLifecycleOwner) { enabled ->
            if (offlineSwitch.isChecked != enabled) {
                offlineSwitch.isChecked = enabled
            }
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkThemeEnabled(isChecked)
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        offlineSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setOfflineEnabled(isChecked)
            Toast.makeText(
                requireContext(),
                if (isChecked) "Оффлайн режим включен" else "Оффлайн режим выключен",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "SettingsFragment"
    }
}
