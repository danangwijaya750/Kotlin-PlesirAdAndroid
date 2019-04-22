package com.dngwjy.plesirads.presenters

import android.net.Uri
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.util.logE
import com.dngwjy.plesirads.views.PaymentConfView
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class PaymentConfirmationPresenter(val view:PaymentConfView,val db:FirebaseFirestore,val fAuth:FirebaseAuth,val storageReference: StorageReference) {
    fun confirmData(orderId:String,uriFile:Uri,fileType:String){
        view.isLoading(true)
        val updates = HashMap<String,Any>()
        updates.put("order_status", "Menunggu Konfirmasi")

        val storageReference2nd =
            storageReference.child("All_Image_Uploads/"+ fAuth.currentUser?.uid+System.currentTimeMillis() + "." + fileType)
        val urlTask= storageReference2nd.putFile(uriFile).continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation storageReference2nd.downloadUrl
        }).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                updates.put("transfer_image", downloadUri.toString())
                logD("downurl ${downloadUri}")
                db.collection("ad_space_order").document(orderId).update(updates).addOnSuccessListener {
                    view.confirmPayment(true)
                    view.isLoading(false)
                }.addOnFailureListener {
                    logE(it.localizedMessage)
                    view.confirmPayment(false)
                    view.isLoading(false)
                }
            } else {
                view.confirmPayment(false)
                view.isLoading(false)
            }
        }



    }
}