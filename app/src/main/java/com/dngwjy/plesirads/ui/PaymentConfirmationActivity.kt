package com.dngwjy.plesirads.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.models.Order
import com.dngwjy.plesirads.presenters.PaymentConfirmationPresenter
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.util.toast
import com.dngwjy.plesirads.views.PaymentConfView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_payment_confirmation.*
import java.text.NumberFormat
import java.util.*

class PaymentConfirmationActivity : AppCompatActivity(),PaymentConfView {
    override fun isLoading(state: Boolean) {
        when(state){
            true->{
                progressBar3.visibility= View.VISIBLE
                confButton.visibility= View.GONE
            }
            false->{
                progressBar3.visibility= View.GONE
                confButton.visibility= View.VISIBLE
            }
        }
    }

    override fun confirmPayment(state:Boolean) {
        when(state){
            true->{
                toast("Konfirmasi Pembayaran Berhasil, Silahkan menunggu Konfirmasi dari Admin")
                onBackPressed()
            }
            false->{
                toast("Konfirmasi Pembayaran Gagal, Silahkan Coba beberapa saat lagi")
            }
        }
    }
    lateinit var FileUri: Uri
    private var isImageSelected=false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==7&&resultCode== Activity.RESULT_OK&&data!=null&&data.data!=null){
            FileUri=data.data
            try{
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,FileUri)
                ShowImageView.setImageBitmap(bitmap)
                isImageSelected=true
                logD("$isImageSelected")
            }catch (ex:Exception){
                Log.d("exection",ex.localizedMessage)
            }
        }else{
            logD("kosong")
        }
    }

    lateinit var data:Order
    lateinit var presenter:PaymentConfirmationPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)
        supportActionBar?.hide()
        getData()
        showData()
        val db=FirebaseFirestore.getInstance()
        val fAuth=FirebaseAuth.getInstance()
        val storage=FirebaseStorage.getInstance().reference
        presenter=PaymentConfirmationPresenter(this,db,fAuth,storage)
        confButton.setOnClickListener {
            when(isImageSelected) {
                true ->  { presenter.confirmData(data.id,FileUri,fileExtension(FileUri))

                }
                false -> toast("Siliahkan Pilih Bukti Foto Bukti Transaksi Terlebih Dahulu")
            }
        }
        backBtn.setOnClickListener{
            onBackPressed()
        }
        upButton.setOnClickListener {
            val intent= Intent().setType("image/*")
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Select Image"),7)
        }
    }
    private fun getData(){
        data=intent.extras.getParcelable("data")
    }
    private fun showData(){
        pricingText.text="Rp. ${NumberFormat.getNumberInstance(Locale.US).format(data.harga_total.toLong())}"
        when(data.order_status=="Belum dibayar"){
            true->{
                confButton.isEnabled=true
            }
            false->{
                confButton.isEnabled=false
            }
        }
    }
    fun fileExtension(fileUri: Uri): String {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(fileUri))
    }


    override fun onBackPressed() {
      startActivity(Intent(this,PaymentActivity::class.java))
        finish()
    }
}
