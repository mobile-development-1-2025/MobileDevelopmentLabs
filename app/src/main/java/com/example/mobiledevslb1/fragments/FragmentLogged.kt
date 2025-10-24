package com.example.mobiledevslb1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class FragmentLogged: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("ACTIVITY", "Called ${this::class.simpleName}.onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }
}