package com.m.labs_dk_lab1.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.m.labs_dk_lab1.R
import com.m.labs_dk_lab1.util.Logger

class ProfileFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.log("ProfileFragment attached")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.log("ProfileFragment created")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.log("ProfileFragment view created")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.log("ProfileFragment view ready")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.log("ProfileFragment view destroyed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.log("ProfileFragment destroyed")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.log("ProfileFragment detached")
    }
}
