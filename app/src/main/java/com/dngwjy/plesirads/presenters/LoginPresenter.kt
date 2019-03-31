package com.dngwjy.plesirads.presenters

import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.views.LoginView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginPresenter(val view: LoginView,val fAuth: FirebaseAuth,val db:FirebaseFirestore) {
    fun checkLogged():Boolean{
        var result=false
        if(fAuth.currentUser!=null){
            result=true
        }
       return result
    }
     fun checkrole(){
        val query=db.collection("user").document(fAuth.currentUser!!.uid)
        query.get().addOnSuccessListener {
            logD("${it.get("role")}")
            if(it.get("role").toString().trim() == "kRj4EDag7X5A4ExxjIdr"){
                view.showResult(true,"")
            }else{
                fAuth.signOut()
                view.showResult(false,"wrong role")
            }

        }

    }
    fun doLogin(email:String,passwd:String){
        view.isLoading(true)
        fAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener {
            view.isLoading(false)
            when (it.isSuccessful){
                true->{
                  checkrole()
                }
                false->{
                    view.showResult(false,"wrong user and pass")
                }
            }
        }
    }
}