package com.m.labs_dk_lab1.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.m.labs_dk_lab1.R
import com.m.labs_dk_lab1.util.Logger

class SettingsFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.log("SettingsFragment attached")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.log("SettingsFragment created")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.log("SettingsFragment view created")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.log("SettingsFragment view ready")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.log("SettingsFragment view destroyed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.log("SettingsFragment destroyed")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.log("SettingsFragment detached")
    }
}