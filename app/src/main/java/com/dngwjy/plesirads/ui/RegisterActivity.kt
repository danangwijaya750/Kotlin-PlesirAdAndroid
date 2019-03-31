package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.presenters.RegisterPresenter
import com.dngwjy.plesirads.util.toast
import com.dngwjy.plesirads.views.RegisterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity(),RegisterView {
    override fun showResult(state: Boolean) {
        when(state){
            true->{toast("Register Success")
            startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            false->{
                toast("Register Failed Try Again Later")
            }
        }
    }

    override fun isLoading(state: Boolean) {
        when(state){
            true->{
                pgbar.visibility= View.VISIBLE
            }
            false->{
                pgbar.visibility= View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_register)
        supportActionBar!!.hide()
        val db=FirebaseFirestore.getInstance()
        val fAuth=FirebaseAuth.getInstance()
        val presenter=RegisterPresenter(this,db,fAuth)
        loginTv.setOnClickListener {   startActivity(Intent(this,LoginActivity::class.java))
            finish()}
        registerBtn.setOnClickListener {
            if(validate()){
                presenter.doRegister(nameEdit.text.toString(),usernameEdit.text.toString(),emailEdit.text.toString(),passwdEdit.text.toString())
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
    private fun validate():Boolean{
        var result=true
        if(nameEdit.text.toString().trim()=="")
        {nameEdit.error="Required"; result=false }
        else if(usernameEdit.text.toString().trim()==""){
            usernameEdit.error="Required"; result=false
        } else if(emailEdit.text.toString().trim()=="")
        {emailEdit.error="Required";result=false}
        else if(passwdEdit.text.toString().trim()=="")
        {passwdEdit.error="Required"; result=false }
        else if(ulangpasswdEdit.text.toString().trim()=="")
        {ulangpasswdEdit.error="Required"; result=false }
        else if(!isEmail(emailEdit.text.toString()))
        {emailEdit.error="Wrong email pattern";result=false}
        else if(passwdEdit.text.toString().trim().length<6)
        {passwdEdit.error="Minimum 6 Digit!";result=false}
        else if(passwdEdit.text.toString().trim()!=ulangpasswdEdit.text.toString().trim()){
            ulangpasswdEdit.error="Confirm Password Not same"; result =false }
        return result
    }
    fun isEmail(data:String):Boolean{
        return Pattern.compile("^+.+@+.+\\..+$").matcher(data).matches()
    }
}
