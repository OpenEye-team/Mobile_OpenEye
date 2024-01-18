package com.txtlabs.openeye.ui.tracker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.txtlabs.openeye.R
import com.txtlabs.openeye.data.ResultState
import com.txtlabs.openeye.databinding.FragmentTrackerBinding
import com.txtlabs.openeye.ui.tracker.add.AddTrackerActivity

class TrackerFragment : Fragment() {

    private var _binding: FragmentTrackerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrackerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter()
        getTracker()

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(requireActivity(), AddTrackerActivity::class.java))
        }
    }

    private fun pagerAdapter() {
        val detailPagerAdapter = ViewPagerDetailAdapter(requireActivity())
        val viewPager: ViewPager2 = binding.viewpager
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = binding.tablayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun getTracker() {
        viewModel = ViewModelProvider(this)[TrackerViewModel::class.java]
        viewModel.getGlucoseTracker()
        viewModel.getHistory.observe(viewLifecycleOwner) {
            when (it) {
                is ResultState.Loading -> {

                }

                is ResultState.Success -> {
                    binding.apply {
                        if (it.value.bloodGlucoseAverage != null) {
                            val number = it.value.bloodGlucoseAverage.toInt()
                            persentase.text = "$number md/dl"

                            when (it.value.bloodGlucoseLevel) {
                                "Rendah" -> {
                                    level.setBackgroundColor(Color.YELLOW)
                                }
                                "Normal" -> {
                                    level.setBackgroundColor(Color.GREEN)
                                }
                                "Tinggi" -> {
                                    level.setBackgroundColor(Color.YELLOW)
                                }
                                "Sangat Tinggi" -> {
                                    level.setBackgroundColor(Color.RED)
                                }
                            }
                            textLevel.text = it.value.bloodGlucoseLevel
                        } else {
                            persentase.text = "0 md/dl"
                            textLevel.text = "Empty"
                        }
                    }
                }

                is ResultState.Failure -> {

                }

                else -> {

                }
            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.last_mont,
            R.string.last_week,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}