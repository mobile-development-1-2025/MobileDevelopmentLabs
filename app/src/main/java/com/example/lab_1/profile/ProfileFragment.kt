package com.example.lab_1.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lab_1.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); Log.i(TAG, "onCreate") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { super.onViewCreated(view, savedInstanceState); Log.i(TAG, "onViewCreated") }

    override fun onStart() { super.onStart(); Log.i(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.i(TAG, "onResume") }
    override fun onPause() { Log.i(TAG, "onPause"); super.onPause() }
    override fun onStop() { Log.i(TAG, "onStop"); super.onStop() }
    override fun onDestroyView() { Log.i(TAG, "onDestroyView"); _binding = null; super.onDestroyView() }
    override fun onDestroy() { Log.i(TAG, "onDestroy"); super.onDestroy() }
}
