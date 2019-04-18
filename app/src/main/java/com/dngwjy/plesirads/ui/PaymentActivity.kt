package com.dngwjy.plesirads.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dngwjy.plesirads.R

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar!!.hide()

    }
}
