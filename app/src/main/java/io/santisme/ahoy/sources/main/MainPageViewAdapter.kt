package io.santisme.ahoy.sources.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.santisme.ahoy.sources.main.userdetail.UserDetailFragment

class MainPageViewAdapter(fm: FragmentManager?, lifecycle: Lifecycle, private val fragments: List<MainActivity.Companion.TabLayoutFragment>) :
    FragmentStateAdapter(fm!!, lifecycle) {
    private val tabItems = fragments.count()

    override fun createFragment(position: Int): Fragment {
        return if (position <= fragments.size) {
            fragments[position].fragment
        } else {
            UserDetailFragment()
        }
    }

    override fun getItemCount(): Int {
        return tabItems
    }

}