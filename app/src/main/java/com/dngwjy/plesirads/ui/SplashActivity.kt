package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.dngwjy.plesirads.R

class SplashActivity : AppCompatActivity() {

    private var mDelay: Handler? = null
    private val SDELAY :Long = 2000

      val mRunnable : Runnable = Runnable {
        if(!isFinishing){
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        mDelay = Handler()
        mDelay!!.postDelayed(mRunnable, SDELAY)
    }
    public override fun onDestroy(){
        if (mDelay != null) {
            mDelay!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }

}
