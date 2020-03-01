package com.leosimas.udsagenda.ui.agendalist

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.leosimas.udsagenda.R

class AgendaListPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = arrayOf(
        R.string.open_agendas,
        R.string.closed_agendas
    )

    private val tabFilters = arrayOf(
        AgendaFilter.OPEN,
        AgendaFilter.FINISHED
    )

    override fun getItem(position: Int): Fragment =
        AgendaListFragment.newInstance(tabFilters[position])

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(tabTitles[position])

    override fun getCount(): Int = tabFilters.size
}