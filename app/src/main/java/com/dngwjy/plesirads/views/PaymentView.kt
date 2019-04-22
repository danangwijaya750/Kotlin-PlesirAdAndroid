package com.dngwjy.plesirads.views

import com.dngwjy.plesirads.data.models.Order

interface PaymentView {
    fun isLoading(state:Boolean)
    fun showPayment(state:Boolean,msg:String)
    fun showPaymentData(data:List<Order>)
}