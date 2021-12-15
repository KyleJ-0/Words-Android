package com.kylerjackson.words.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabPagerAdapter(fm:FragmentManager, private var tabCount:Int): FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    //Returns the fragment associated with each tab in the tablayout
    override fun getItem(position:Int):Fragment{
        when(position){
            0->return DefinitionFragment()
            1->return EtymologyFragment()
            else->return DefinitionFragment()
        }
    }

    override fun getCount():Int{
        return tabCount
    }
}