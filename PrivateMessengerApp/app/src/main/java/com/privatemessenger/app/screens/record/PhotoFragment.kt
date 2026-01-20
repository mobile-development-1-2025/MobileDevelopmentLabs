package com.privatemessenger.app.screens.record

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.common.utils.views.doOnApplyWindowInsets
import com.privatemessenger.app.databinding.FragmentPhotoBinding
import com.privatemessenger.app.screens.chat.ChatFragment

class PhotoFragment : Fragment() {
    private lateinit var binding: FragmentPhotoBinding

    private val args: PhotoFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.doOnApplyWindowInsets { view, insets, rect ->
            view.updatePadding(
                top = rect.top + insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
            )
            insets
        }
        binding.cameraPreviewView.doOnApplyWindowInsets { view, insets, rect ->
            view.updatePadding(
                bottom = 0
            )
            insets
        }
        val photoUriString = args.photo
        val photoUri = Uri.parse(photoUriString)
        Glide.with(binding.cameraPreviewView)
            .load(photoUri)
            .into(binding.cameraPreviewView)


        binding.sendBtn.setOnClickListener {
            setFragmentResult(
                ChatFragment.FILE_CAPTURED_REQUEST_KEY, bundleOf(
                    ChatFragment.FILE_CAPTURED_BUNDLE_KEY to args.photo
                )
            )
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
        binding.cancelBtn.setOnClickListener {
            findNavController().navigate(NavGraphDirections.startRecorderFragment())
        }
        binding.backBtn.setOnClickListener {
            findNavController().navigate(NavGraphDirections.startRecorderFragment())
        }
    }

}