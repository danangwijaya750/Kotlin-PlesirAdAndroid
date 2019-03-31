package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.dngwjy.plesirads.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        initation()
    }
    private fun initation(){
        semuaLay.setOnClickListener {replaceActivity("all")}
        reklameLay.setOnClickListener {replaceActivity("reklame")}
        balihoLay.setOnClickListener {replaceActivity("baliho")}
        cetakLay.setOnClickListener {replaceActivity("poster")}
        toolsLay.setOnClickListener {replaceActivity("peralatan")}
        acaraLay.setOnClickListener {replaceActivity("acara")}
        logout.setOnClickListener {
            val fAuth=FirebaseAuth.getInstance()
            fAuth.signOut()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
    private fun replaceActivity(type:String){
        val intent= Intent(this,ListSpaceActivity::class.java)
        intent.putExtra("request",type)
        startActivity(intent)
        finish()
    }

}
