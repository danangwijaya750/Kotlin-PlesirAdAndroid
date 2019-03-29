package com.dngwjy.plesirads.presenters

import com.dngwjy.plesirads.views.LoginView
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(val view: LoginView,val fAuth: FirebaseAuth) {
    fun checkLogged():Boolean{
       return fAuth.currentUser!=null
    }

    fun doLogin(email:String,passwd:String){
        view.isLoading(true)
        fAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener {
            view.isLoading(false)
            val res=it.isSuccessful
            view.showResult(res)
        }
    }
}