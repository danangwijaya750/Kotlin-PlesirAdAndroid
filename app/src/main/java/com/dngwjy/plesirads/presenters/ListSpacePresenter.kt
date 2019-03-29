package com.dngwjy.plesirads.presenters


import com.dngwjy.plesirads.data.models.Space
import com.dngwjy.plesirads.views.ListSpaceView
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class ListSpacePresenter(val view: ListSpaceView, val db:FirebaseFirestore) {
    var data:MutableList<Space> = mutableListOf()
    fun getAllSpace(){
        view.isLoading(true)
        val query:Query=db.collection("").orderBy("").limit(1)
        query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException!=null){
                return@addSnapshotListener
            }
            if (querySnapshot != null) {
                data.addAll(querySnapshot.toObjects(Space::class.java))
            }
            view.showData(data)
            view.isLoading(false)
        }
    }
    fun getReklame(){
        view.isLoading(true)
        val query:Query=db.collection("").orderBy("").limit(1)
        query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException!=null){
                return@addSnapshotListener
            }
            if (querySnapshot != null) {
                data.addAll(querySnapshot.toObjects(Space::class.java))
            }
            view.showData(data)
            view.isLoading(false)
        }
    }


}