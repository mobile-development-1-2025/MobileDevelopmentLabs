package com.example.vsemk.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SettingsFragment", "onCreateView")

        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        layout.addView(TextView(requireContext()).apply {
            text = "Настройки"
            textSize = 18f
        })

        layout.addView(Switch(requireContext()).apply {
            text = "Тёмная тема"
        })

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("SettingsFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("SettingsFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SettingsFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SettingsFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SettingsFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("SettingsFragment", "onDestroyView")
    }
}