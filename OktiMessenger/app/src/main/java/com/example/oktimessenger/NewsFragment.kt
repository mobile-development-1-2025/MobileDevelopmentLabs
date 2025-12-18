package com.example.oktimessenger

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oktimessenger.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {


    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewsViewModel by viewModels()
    private val adapter = MessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.submitList(it.toList())  // создаём новый объект списка
        }

        binding.btnRefresh.setOnClickListener {
            Log.d("NewsFragment", "Refresh clicked") // проверка кнопки
            viewModel.loadMessages()
        }

        viewModel.loadMessages()  // начальная загрузка

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
