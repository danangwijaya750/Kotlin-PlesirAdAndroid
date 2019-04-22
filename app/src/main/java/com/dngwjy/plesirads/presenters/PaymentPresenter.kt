package com.dngwjy.plesirads.presenters

import com.dngwjy.plesirads.data.models.Order
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.views.PaymentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class PaymentPresenter(val view:PaymentView,val db:FirebaseFirestore,val fAuth:FirebaseAuth) {
fun getData(){
     var data:MutableList<Order> = mutableListOf()
    view.isLoading(true)
    val query: Query =db.collection("ad_space_order").whereEqualTo("user", fAuth.currentUser?.uid).orderBy("order_date",
        Query.Direction.DESCENDING)

    query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
        if(firebaseFirestoreException!=null){
            return@addSnapshotListener
        }
        if(querySnapshot!=null){
            data.clear()
            view.isLoading(true)
            logD("data length ${querySnapshot.size()}")
            querySnapshot.forEach {
                val q2=db.collection("ad_space").document(it["ad_space"].toString())
                q2.get().addOnSuccessListener {doc->
                    logD("space is ${doc["name"]}")
                    data.add(Order(
                        id=it.id,
                        space_name = doc["name"].toString(),
                        ad_space = it["ad_space"].toString(),
                        order_status = it["order_status"].toString(),
                        start_date = it["start_date"].toString(),
                        end_date = it["end_date"].toString(),
                        order_date = it["order_date"].toString(),
                        harga_satuan = it["harga_satuan"].toString(),
                        harga_total = it["harga_total"].toString()
                    ))
                    view.showPaymentData(data)
                }

            }
            view.isLoading(false)
        }
    }

}
}