package com.dngwjy.plesirads.views

interface PaymentView {
    fun isLoading(state:Boolean)
    fun showPayment(state:Boolean,msg:String)
}