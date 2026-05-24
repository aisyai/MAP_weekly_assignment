package com.example.lab_week_04

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

val TABS_FIXED = listOf(
    R.string.fore_title,
    R.string.janjijiwa_title,
    R.string.kopikenangan_title,
)

val TABS_DESC = listOf(
    R.string.fore_desc,
    R.string.janjijiwa_desc,
    R.string.kopikenangan_desc,
)

class CafeAdapter(
    private val ctx: Context,
    fragmentManager: FragmentManager, lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return TABS_FIXED.size
    }

    override fun createFragment(position: Int): Fragment
    {
        val desc = ctx.getString(TABS_DESC[position])
        return CafeDetailFragment.newInstance(desc)
    }
}

