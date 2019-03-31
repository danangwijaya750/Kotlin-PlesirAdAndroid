package com.dngwjy.plesirads.presenters

import com.dngwjy.plesirads.util.logE
import com.dngwjy.plesirads.views.RegisterView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterPresenter(val view:RegisterView,val db:FirebaseFirestore,val fAuth:FirebaseAuth) {
fun doRegister(name:String, username:String, email:String,passwd:String){
    view.isLoading(true)
    fAuth.createUserWithEmailAndPassword(email,passwd).addOnCompleteListener {
        when(!it.isSuccessful){
            true->{
                logE(it.exception.toString())
                view.showResult(false)
                view.isLoading(false)
            }
            false->{
                it.let {
                    addUserToDB(name,username,email,passwd, it.result!!.user.uid)
                }
                view.isLoading(false)
            }
        }
    }
}
    private fun addUserToDB(name:String, username:String, email:String,passwd:String,uid:String){
        try {
            val items = HashMap<String, Any>()
            items.put("name", name)
            items.put("username", username)
            items.put("email", email)
            items.put("role", "kRj4EDag7X5A4ExxjIdr")
            db.collection("user").document(uid).set(items).addOnSuccessListener {
                view.showResult(true)
            }.addOnFailureListener {
                view.showResult(false)
                    logE(it.localizedMessage)
                it.stackTrace
            }
        }catch (e:Exception) {
            logE(e.toString())
        }
    }
}