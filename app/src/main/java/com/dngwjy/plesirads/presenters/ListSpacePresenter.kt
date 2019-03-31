package com.dngwjy.plesirads.presenters


import com.dngwjy.plesirads.data.models.Space
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.views.ListSpaceView
import com.google.firebase.firestore.*

class ListSpacePresenter(val view: ListSpaceView, val db:FirebaseFirestore) {
    var data:MutableList<Space> = mutableListOf()

    fun getAllSpace(){
        view.isLoading(true)
        val query:Query=db.collection("ad_space")
        query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException!=null){
                return@addSnapshotListener
            }
            if (querySnapshot != null) {
                data.clear()
                querySnapshot.forEach {
                 data.add(Space(
                     id = it.id,
                     namaReklame = it["name"].toString(),
                     deskripsi=it["deskripsi"].toString(),
                     rating=it["rating"].toString(),
                     spaceType=it["space_type"].toString(),
                     address=it["address"].toString(),
                     price=it["price"].toString(),
                     visitors=it["visitor"] as Long,
                     phone=it["phone"].toString(),
                     photo = it["photo"].toString(),
                     heightSpace = it["height"] as Long,
                     widthSpace = it["width"] as Long
                 ))
                }
//                data.addAll(querySnapshot.toObjects(Space::class.java))
                logD("data ${data.size}")
            }
            view.showData(data)
            view.isLoading(false)
        }
    }
    fun getSpaceBySearch(param:String){
        view.isLoading(true)
        val query:Query=db.collection("ad_space").whereArrayContains("address",param)
        query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException!=null){
                return@addSnapshotListener
            }
            if (querySnapshot != null) {
                data.clear()
                querySnapshot.forEach {
                    data.add(Space(
                        id = it.id,
                        namaReklame = it["name"].toString(),
                        deskripsi=it["deskripsi"].toString(),
                        rating=it["rating"].toString(),
                        spaceType=it["space_type"].toString(),
                        address=it["address"].toString(),
                        price=it["price"].toString(),
                        visitors=it["visitor"] as Long,
                        phone=it["phone"].toString(),
                        photo = it["photo"].toString(),
                        heightSpace = it["height"] as Long,
                        widthSpace = it["width"] as Long
                    ))
                }
                logD("size ${data.size}")
            }
            view.showData(data)
            view.isLoading(false)
        }
    }
    fun getSpaceByType(param:String){
        view.isLoading(true)
        val query:Query=db.collection("ad_space").whereEqualTo("space_type",param)
        query.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException!=null){
                return@addSnapshotListener
            }
            if (querySnapshot != null) {
                data.clear()
                querySnapshot.forEach {
                    data.add(Space(
                        id = it.id,
                        namaReklame = it["name"].toString(),
                        deskripsi=it["deskripsi"].toString(),
                        rating=it["rating"].toString(),
                        spaceType=it["space_type"].toString(),
                        address=it["address"].toString(),
                        price=it["price"].toString(),
                        visitors=it["visitor"] as Long,
                        phone=it["phone"].toString(),
                        photo = it["photo"].toString(),
                        heightSpace = it["height"] as Long,
                        widthSpace = it["width"] as Long
                    ))
                }
                logD("size ${data.size}")
            }
            view.showData(data)
            view.isLoading(false)
        }
    }


}