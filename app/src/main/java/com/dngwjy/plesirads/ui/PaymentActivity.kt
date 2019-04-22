package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.adapters.PaymentAdapter
import com.dngwjy.plesirads.data.models.Order
import com.dngwjy.plesirads.presenters.PaymentPresenter
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.views.PaymentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_space.*
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_payment.backBtn

class PaymentActivity : AppCompatActivity(),PaymentView {
    override fun showPaymentData(data: List<Order>) {
        logD("data to show ${data.size}")
        data.forEach {
            logD(it.id)
        }
        dataOrder.clear()
        dataOrder.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private lateinit var presenter :PaymentPresenter
    var dataOrder:MutableList<Order> = mutableListOf()
    lateinit var adapter: PaymentAdapter
    override fun isLoading(state: Boolean) {
        swiperPayment.isRefreshing=state
    }

    override fun showPayment(state: Boolean, msg: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar!!.hide()
        backBtn.setOnClickListener {
            onBackPressed()
        }
        initiation()
        presenter.getData()
    }
    private fun initiation(){
        val db=FirebaseFirestore.getInstance()
        val fAuth=FirebaseAuth.getInstance()
        presenter=PaymentPresenter(this,db,fAuth)
        adapter= PaymentAdapter(dataOrder){
            val intent= Intent(this,PaymentConfirmationActivity::class.java)
            intent.putExtra("data",it)
            startActivity(intent)
            this.finish()
        }
        val layMan= LinearLayoutManager(this)
        layMan.orientation= LinearLayoutManager.VERTICAL
        recPayment.layoutManager=layMan
        recPayment.adapter=adapter
        swiperPayment.setOnRefreshListener {
            presenter.getData()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
