package com.dngwjy.plesirads.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.models.Order
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.payment_item.*

class PaymentAdapter(val data:List<Order>,val listener:(Order)->Unit):RecyclerView.Adapter<PaymentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.payment_item,p0,false))
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindData(data[p1],listener)
    }

    class ViewHolder(override val containerView: View):RecyclerView.ViewHolder(containerView),LayoutContainer {
        fun bindData(datanya:Order,listen:(Order)->Unit){
            orderId.text=datanya.space_name
            statusOrder.text=datanya.order_status
            orderDate.text=datanya.order_date
            itemView.setOnClickListener { listen(datanya) }
        }
    }
}