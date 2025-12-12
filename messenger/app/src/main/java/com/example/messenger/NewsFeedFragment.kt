package com.example.messenger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private val TAG = "LifeCycleLog"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "NewsFeedFragment: onAttach() – Присоединение к Activity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "NewsFeedFragment: onCreate()")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "NewsFeedFragment: onViewCreated() – Разметка создана")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "NewsFeedFragment: onStart() – Фрагмент виден")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "NewsFeedFragment: onResume() – Фрагмент активен")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "NewsFeedFragment: onPause() – Фрагмент теряет фокус")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "NewsFeedFragment: onStop() – Фрагмент не виден")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "NewsFeedFragment: onDestroyView() – Разметка уничтожена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NewsFeedFragment: onDestroy() – Фрагмент уничтожен")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "NewsFeedFragment: onDetach() – Отсоединение от Activity")
    }
}