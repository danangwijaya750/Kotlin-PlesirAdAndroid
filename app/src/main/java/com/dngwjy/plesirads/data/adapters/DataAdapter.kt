package com.dngwjy.plesirads.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.models.Space
import kotlinx.android.extensions.LayoutContainer

class DataAdapter(val data:List<Space>,val listener:(Space)->Unit):RecyclerView.Adapter<DataAdapter.DataHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataHolder {
        return DataHolder(LayoutInflater.from(p0.context).inflate(R.layout.iklan_item,p0,false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(p0: DataHolder, p1: Int) {
        p0.bindData(data[p1],listener)
    }

    class DataHolder(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bindData(space: Space,listen:(Space)->Unit){
            itemView.setOnClickListener { listen(space) }
        }
    }
}