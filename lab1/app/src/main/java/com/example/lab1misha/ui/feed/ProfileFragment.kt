package com.example.messenger.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.R

class ProfileFragment : Fragment() {

    private val logTag = "ProfileFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        log("View создан")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("View готов к использованию")
    }

    override fun onStart() {
        super.onStart()
        log("Fragment запущен")
    }

    override fun onResume() {
        super.onResume()
        log("Fragment возобновлен")
    }

    override fun onPause() {
        log("Fragment приостановлен")
        super.onPause()
    }

    override fun onStop() {
        log("Fragment остановлен")
        super.onStop()
    }

    override fun onDestroyView() {
        log("View уничтожен")
        super.onDestroyView()
    }

    override fun onDestroy() {
        log("Fragment уничтожен")
        super.onDestroy()
    }

    private fun log(message: String) {
        Log.d(logTag, message)
    }
}
