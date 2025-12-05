package com.m.labs_dk_lab1.ui.feed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.m.labs_dk_lab1.R
import com.m.labs_dk_lab1.util.Logger

class FeedFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.log("FeedFragment attached")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.log("FeedFragment created")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.log("FeedFragment view created")
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.log("FeedFragment view ready")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Logger.log("FeedFragment view destroyed")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.log("FeedFragment destroyed")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.log("FeedFragment detached")
    }
}
