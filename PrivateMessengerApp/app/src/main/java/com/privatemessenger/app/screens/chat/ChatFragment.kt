package com.privatemessenger.app.screens.chat

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devlomi.record_view.OnRecordListener
import com.devlomi.record_view.RecordPermissionHandler
import com.privatemessenger.app.BuildConfig
import com.privatemessenger.app.NavGraphDirections
import com.privatemessenger.app.R
import com.privatemessenger.app.common.utils.files.InputStreamRequestBody
import com.privatemessenger.app.common.utils.views.doOnApplyWindowInsets
import com.privatemessenger.app.databinding.FragmentChatBinding
import com.privatemessenger.app.screens.chat.adapter.ChatAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding

    private val args: ChatFragmentArgs by navArgs()
    private val model: ChatViewModel by viewModels()

    private val adapter = ChatAdapter {
        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            File(it.filePath!!)
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(
            uri, InputStreamRequestBody.getMimeType(
                requireContext(), uri
            )
        )
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION

        try {
            startActivity(Intent.createChooser(intent, "Open with"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var isRecording = false
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var audioFile: File

    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val fileUri: Uri? = result.data?.data
                if (fileUri != null) {
                    model.sendFile(fileUri, args.peer)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(LayoutInflater.from(requireActivity()))
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

        binding.inputLayout.doOnApplyWindowInsets { view, insets, rect->
            view.updatePadding(
                bottom = rect.bottom + if (insets.getInsets(WindowInsetsCompat.Type.ime()).bottom == 0) {
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                } else {
                    insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                },
            )
            insets
        }
        binding.recordView.setSlideToCancelTextColor(requireContext().getColor(R.color.white))
        binding.recordView.setSlideToCancelArrowColor(requireContext().getColor(R.color.white))
        binding.recordView.setCounterTimeColor(requireContext().getColor(R.color.white))

        binding.recordView.setTrashIconColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.rubbish_color
            )
        )

        binding.backBtn.setOnClickListener {
            findNavController().navigate(NavGraphDirections.startChatListFragment())
        }
        binding.peerNameText.text = args.peer.username
        binding.voiceBtn.setRecordView(binding.recordView)
        model.initPaging(args.peer)
        viewLifecycleOwner.lifecycleScope.launch {
            model.messagesFlow.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.input.addTextChangedListener {
            binding.sendBtn.isVisible = it.toString().isNotEmpty()
            binding.fileBtn.isVisible = it.toString().isEmpty()
            binding.photoBtn.isVisible = it.toString().isEmpty()
            binding.voiceBtn.isVisible = it.toString().isEmpty()
        }

        binding.fileBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "*/*" // Allow any file type
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            try {
                filePickerLauncher.launch(Intent.createChooser(intent, "Select a file"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.sendBtn.setOnClickListener {
            val message = binding.input.text.toString()
            if (message.isNotEmpty()) {
                model.sendMessage(message, args.peer)
                binding.input.setText("")
            }
        }

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, true)

        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                delay(5000L)
                adapter.refresh()
            }
        }

        binding.photoBtn.setOnClickListener {
            findNavController().navigate(NavGraphDirections.startRecorderFragment())
        }

        binding.recordView.setOnRecordListener(object : OnRecordListener {
            override fun onStart() {
                startRecording()
                binding.voiceBtn.setImageResource(R.drawable.mic_white)
                binding.recordView.setSlideToCancelTextColor(requireContext().getColor(R.color.white))
                binding.voiceBtn.setBackgroundResource(R.drawable.revc_background)
                binding.input.visibility = View.GONE
                binding.fileBtn.visibility = View.GONE
                binding.photoBtn.visibility = View.GONE
            }

            override fun onCancel() {
                stopRecording()
                binding.voiceBtn.setImageResource(R.drawable.ic_mic)
                binding.voiceBtn.setBackgroundColor(Color.TRANSPARENT)
                binding.input.visibility = View.VISIBLE
                binding.fileBtn.visibility = View.VISIBLE
                binding.photoBtn.visibility = View.VISIBLE
            }

            override fun onFinish(recordTime: Long, limitReached: Boolean) {
                stopRecording()
                binding.voiceBtn.setImageResource(R.drawable.ic_mic)
                binding.voiceBtn.setBackgroundColor(Color.TRANSPARENT)
                binding.input.visibility = View.VISIBLE
                binding.fileBtn.visibility = View.VISIBLE
                binding.photoBtn.visibility = View.VISIBLE

                val uri = FileProvider.getUriForFile(
                    requireContext(),
                    "${BuildConfig.APPLICATION_ID}.provider",
                    audioFile
                )
                model.sendFile(uri, args.peer)
            }

            override fun onLessThanSecond() {
                binding.voiceBtn.setImageResource(R.drawable.ic_mic)
                binding.voiceBtn.setBackgroundColor(Color.TRANSPARENT)
                binding.input.visibility = View.VISIBLE
                binding.fileBtn.visibility = View.VISIBLE
                binding.photoBtn.visibility = View.VISIBLE

            }

            override fun onLock() {
            }
        })

        binding.recordView.setRecordPermissionHandler(RecordPermissionHandler {
            val recordPermissionAvailable = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) == PERMISSION_GRANTED
            if (recordPermissionAvailable) {
                return@RecordPermissionHandler true
            }
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO),
                0
            )
            false
        })

        setFragmentResultListener(FILE_CAPTURED_REQUEST_KEY) { _, bundle ->
            model.sendFile(bundle.getString(FILE_CAPTURED_BUNDLE_KEY)!!.toUri(), args.peer)
        }
    }

    private fun startRecording() {
        mediaRecorder = MediaRecorder()

        audioFile = File(requireContext().filesDir, "audio.mp3")
        if (audioFile.exists()) {
            audioFile.delete()
        }

        mediaRecorder.setOutputFile(audioFile.absolutePath)
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
            isRecording = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder.stop()
            mediaRecorder.release()
            isRecording = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isRecording) {
            stopRecording()
        }
    }

    companion object {
        const val FILE_CAPTURED_REQUEST_KEY = "FILE_CAPTURED_REQUEST_KEY"
        const val FILE_CAPTURED_BUNDLE_KEY = "FILE_CAPTURED_BUNDLE_KEY"
    }
}