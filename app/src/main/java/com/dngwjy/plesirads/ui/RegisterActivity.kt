package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.dngwjy.plesirads.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_register)
        supportActionBar!!.hide()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}
