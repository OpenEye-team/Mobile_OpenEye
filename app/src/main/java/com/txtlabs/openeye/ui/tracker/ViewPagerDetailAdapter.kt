package com.txtlabs.openeye.ui.tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerDetailAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        val fragment = TrackerDataFragment()
        fragment.arguments = Bundle().apply {
            putInt(TrackerDataFragment.ARG_POSITION.toString(), position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}