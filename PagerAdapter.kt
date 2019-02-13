package com.example.groebe.myapplication

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * A class that effectively manages the use of multiple fragments, or pages, in this application.
 *
 * @author Andrew Groebe.
 */
class PagerAdapter internal constructor(manager: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(manager) {

    val count: Int
        get() = fragments.size

    fun getItem(i: Int): Fragment {
        return fragments[i]
    }
}
