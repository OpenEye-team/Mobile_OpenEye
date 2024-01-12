package com.txtlabs.openeye.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.txtlabs.openeye.databinding.FragmentDataHistoryBinding

class DataHistoryFragment : Fragment() {

    private var _binding: FragmentDataHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        val position = arguments?.getInt(ARG_POSITION.toString(), 0)
        if(position == 1) {
            binding.text.text = "Screen 1"
        } else {
            binding.text.text = "Screen 2"
        }
    }

    companion object {
        const val ARG_POSITION = 0
    }
}