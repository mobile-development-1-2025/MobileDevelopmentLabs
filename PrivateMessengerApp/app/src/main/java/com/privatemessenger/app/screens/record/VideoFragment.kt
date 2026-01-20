package com.privatemessenger.app.screens.record

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.common.utils.views.doOnApplyWindowInsets
import com.privatemessenger.app.databinding.FragmentVideoBinding
import com.privatemessenger.app.screens.chat.ChatFragment

class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private lateinit var player: ExoPlayer
    private val args: VideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
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
        binding.sendBtn.setOnClickListener {
            setFragmentResult(
                ChatFragment.FILE_CAPTURED_REQUEST_KEY, bundleOf(
                    ChatFragment.FILE_CAPTURED_BUNDLE_KEY to args.video
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
        player = ExoPlayer.Builder(requireContext()).build()
        binding.cameraPreviewView.player = player

        val videoUri = Uri.parse(args.video)
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)

        player.repeatMode = Player.REPEAT_MODE_ONE

        player.prepare()
        player.play()
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }
}