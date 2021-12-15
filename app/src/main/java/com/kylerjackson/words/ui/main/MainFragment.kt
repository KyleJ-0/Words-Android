package com.kylerjackson.words.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kylerjackson.words.R
import com.google.android.material.tabs.TabLayout
import android.net.Uri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.main_fragment.*


class MainFragment : Fragment(), DefinitionFragment.OnFragmentInteractionListener, EtymologyFragment.OnFragmentInteractionListener {
    /*
        This main fragment is responsible for containing the search button, tabs, and the text input field.
     */
    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
        configureTabLayout()

        //passes the text found in the edit text field to the view model
        search_button.setOnClickListener{
            viewModel.searchWord(word_input.text.toString())
        }


        val resultWordObserver = Observer<String>{
                result->result_word.text = result
        }
        viewModel.word.observe(viewLifecycleOwner,resultWordObserver)
    }

    //Adds two tabs; Definitions, Etymologies. Also sets up the pager adapter
    private fun configureTabLayout(){
        tab_layout.addTab(tab_layout.newTab().setText("Definitions"))
        tab_layout.addTab(tab_layout.newTab().setText("Etymologies"))

        val adapter = TabPagerAdapter(requireActivity().supportFragmentManager,tab_layout.tabCount)
        pager.adapter = adapter

        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab:TabLayout.Tab){
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

}