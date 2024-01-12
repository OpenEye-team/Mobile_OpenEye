package com.txtlabs.openeye.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import com.txtlabs.openeye.databinding.FragmentHistoryBinding
import com.txtlabs.openeye.R

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagerAdapter()
    }

    private fun pagerAdapter(){
        val detailPagerAdapter = ViewPagerDetailAdapter(requireActivity())
        val viewPager: ViewPager2 = binding.viewpager
        viewPager.adapter = detailPagerAdapter
        val tabs: TabLayout = binding.tablayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.last_mont,
            R.string.last_week,
        )
    }
}