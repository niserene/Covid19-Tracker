package com.example.covid_19tracker

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*

class StateItemAdapter(val list: List<statewiseItem>): RecyclerView.Adapter<StateItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_list, parent, false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item:statewiseItem){

            itemView.stateTv.text = item.state

            itemView.confirmTV.text = SpannableDelta("${item.confirmed} \n ↑${item.deltaconfirmed?:0}",
            "#D32F2F", item.confirmed?.length?:0)

            itemView.activeTv.text = SpannableDelta("${item.active} \n ↑${item.deltaconfirmed ?:0}",
                    "#1976D2", item.confirmed?.length?:0)

            itemView.recoveredTv.text = SpannableDelta("${item.recovered} \n ↑${item.deltarecovered?:0}",
                    "#388E3C", item.confirmed?.length?:0)

            itemView.deathTv.text = SpannableDelta("${item.deaths} \n ↑${item.deltadeaths?:0}",
                    "#FBC02D", item.confirmed?.length?:0)
        }
    }
}
