package com.example.vsemk.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ProfileFragment", "onCreateView")
        return TextView(requireContext()).apply {
            text = "Имя: Семен\nTg: https://t.me/Semenyshka"
            setPadding(32, 32, 32, 32)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ProfileFragment", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ProfileFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ProfileFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ProfileFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ProfileFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("ProfileFragment", "onDestroyView")
    }
}