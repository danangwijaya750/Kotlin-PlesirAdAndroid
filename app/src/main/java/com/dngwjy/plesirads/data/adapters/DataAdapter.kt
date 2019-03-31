package com.dngwjy.plesirads.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.models.Space
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.iklan_item.*
import java.text.NumberFormat
import java.util.*

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
            namaSpace.text=space.namaReklame
            lokasiSpace.text=space.address
            visitorSpace.text="${space.visitors} pengunjung perminggu"
            ratingSpace.text=space.rating
            pricingSpace.text="Rp.${NumberFormat.getNumberInstance(Locale.US).format(space.price.toLong()) }/Bulan"
            Glide.with(containerView).load(space.photo).into(imageView)
            itemView.setOnClickListener { listen(space) }
        }
    }
}