package com.kylerjackson.words

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kylerjackson.words.ui.main.ItemsViewModel

class DefinitionListAdapter(private val dataList: List<ItemsViewModel>): RecyclerView.Adapter<DefinitionListAdapter.ViewHolder>() {
    /*
        This is the adapter list that creates the list for both the definitions fragment and the etymologies fragment
     */
    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(view.context).inflate(R.layout.row_item,view,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val itemViewModel = dataList[position]

        holder.description.text = itemViewModel.description
    }

    override fun getItemCount(): Int{
        return dataList.size
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val description: TextView = itemView.findViewById(R.id.description)
    }
}