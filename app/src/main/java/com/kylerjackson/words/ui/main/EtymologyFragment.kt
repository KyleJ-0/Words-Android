package com.kylerjackson.words.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kylerjackson.words.DefinitionListAdapter
import com.kylerjackson.words.R


class EtymologyFragment : Fragment() {

    lateinit var recyclerView: RecyclerView

    interface OnFragmentInteractionListener{
        fun onFragmentInteraction(uri: Uri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.etymology_fragment, container, false)

        //Get the recycler view that holds the list of etymologies.
        recyclerView = root.findViewById(R.id.etymology_recyclerview)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)

        //Find supporting view model of activity
        var viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //Update data as it changes using live data. The result is a list of each etymology for the given usage of the word.
        val resultObserver = Observer<ArrayList<String>>{
                result->updateList(result)
        }
        viewModel.etymologies.observe(viewLifecycleOwner,resultObserver)
    }

    //this takes each item in result and passes it to a data class 'ItemsViewModel'
    private fun updateList(result: ArrayList<String>){
        var data = ArrayList<ItemsViewModel>()
        var index = 0 //Index is used to append a number to  the start of each etymology (ln.87)
        for(item in result){
            index = index+1
            data.add(ItemsViewModel("($index)    "+item))
        }
        recyclerView.adapter = DefinitionListAdapter(data)
    }

}