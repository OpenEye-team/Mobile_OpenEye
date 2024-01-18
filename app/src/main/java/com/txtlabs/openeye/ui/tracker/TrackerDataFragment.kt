package com.txtlabs.openeye.ui.tracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.databinding.FragmentTrackerDataBinding

class TrackerDataFragment : Fragment() {

    private var _binding: FragmentTrackerDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrackerViewModel
    private lateinit var adapter: ListTrackerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackerDataBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = TrackerViewModel()

        setupData()
    }


    private fun setupData() {
        val position = arguments?.getInt(ARG_POSITION.toString(), 0)
        viewModel.getGlucoseTracker()
        if (position == 1) {
            viewModel.getHistory.observe(viewLifecycleOwner) { dataObserve ->
                when (dataObserve) {
                    is ResultState.Loading -> {

                    }

                    is ResultState.Success -> {

                        Log.d("CEK DATA", "${dataObserve.value.data}")

                        adapter = ListTrackerAdapter(dataObserve.value.data)
                        binding.rvTracker.adapter = adapter
                        binding.rvTracker.setHasFixedSize(true)
                        binding.rvTracker.layoutManager = LinearLayoutManager(requireContext())
                    }

                    is ResultState.Failure -> {

                    }
                }
            }
        } else {
            viewModel.getHistory.observe(viewLifecycleOwner) { dataObserve ->
                when (dataObserve) {
                    is ResultState.Loading -> {

                    }

                    is ResultState.Success -> {
                        adapter = ListTrackerAdapter(dataObserve.value.data)
                        binding.rvTracker.adapter = adapter
                        binding.rvTracker.setHasFixedSize(true)
                        binding.rvTracker.layoutManager = LinearLayoutManager(requireContext())
                    }

                    is ResultState.Failure -> {

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupData()
    }

    companion object {
        const val ARG_POSITION = 0
    }
}