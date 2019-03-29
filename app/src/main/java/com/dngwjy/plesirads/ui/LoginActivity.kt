package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.presenters.LoginPresenter
import com.dngwjy.plesirads.util.isEmail
import com.dngwjy.plesirads.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*
class LoginActivity : AppCompatActivity(),LoginView {
    private lateinit var  presenter:LoginPresenter

    override fun isLoading(state: Boolean) {

    }

    override fun showResult(state: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_login)
        presenter= LoginPresenter(this)
        btn_login.setOnClickListener {
            when(validate()){
                true->{
                    presenter.doLogin()
                }
            }
        }
        create_account.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }
    private fun validate():Boolean{
        var result=true
        if(emailEdit.text.toString().trim()=="")
        {emailEdit.error="Required";result=false}
        else if(passwdEdit.text.toString().trim()=="")
        {passwdEdit.error="Required"; result=false }
        else if(!isEmail(emailEdit.text.toString()))
        {emailEdit.error="Wrong email pattern";result=false}
        else if(passwdEdit.text.toString().trim().length<6)
        {passwdEdit.error="Minimum 6 Digit!";result=false}
        return result
    }
}
