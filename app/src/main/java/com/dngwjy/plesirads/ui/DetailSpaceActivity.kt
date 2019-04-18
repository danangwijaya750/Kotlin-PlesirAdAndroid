package com.dngwjy.plesirads.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Window
import com.bumptech.glide.Glide
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.models.Space
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.util.logE
import com.dngwjy.plesirads.util.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_space.*
import java.text.NumberFormat
import java.util.*
import java.text.SimpleDateFormat
class DetailSpaceActivity : AppCompatActivity() {
lateinit var db:FirebaseFirestore
    lateinit var data:Space
    lateinit var fAuth:FirebaseAuth
    var endDate:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_detail_space)
        supportActionBar!!.hide()
        fAuth=FirebaseAuth.getInstance()
           db= FirebaseFirestore.getInstance()
        data=intent.extras.getParcelable("data")
        textView3.text= data!!.namaReklame
        priceSpace.text= "Rp."+NumberFormat.getNumberInstance(Locale.US).format(data!!.price.toLong())
        alamatSpace.text=data!!.address
        ratingSpace.text=data!!.rating
        visitorSpace.text=data!!.visitors.toString() +"\nPengungjung perminggu"
        Glide.with(this).load(data!!.photo).into(imageView3)
        button.setOnClickListener {
            if(durasiEdit.text!!.isNotEmpty()&&editTanggal.text!!.isNotEmpty()){
                getPrice(data!!.spaceType,data!!.price.toLong(),durasiEdit.text.toString().toLong(),data.widthSpace,data.heightSpace)
            }else{
                toast("all must fill")
            }
        }
        editTanggal.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, cryear, monthOfYear, dayOfMonth ->
                var mnth:String=(monthOfYear+1).toString()
                var dy:String=dayOfMonth.toString()
                if(monthOfYear < 10){
                    mnth = "0" + mnth
                }
                if(dayOfMonth < 10){
                    dy = "0" + dy
                }
                editTanggal.text="${cryear}-${mnth}-${dy}"
            }, year, month, day)

            dpd.show()
        }
    }

    fun getPrice(type:String, price:Long, month:Long, wdth:Long, hght:Long){
        var resul:Long=0
       val query=db.collection("space_type").document(type)
        query.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException!=null){
                return@addSnapshotListener
            }
            if (documentSnapshot != null) {
                resul=documentSnapshot.get("price").toString().toLong()+((wdth*hght)*20000)+(month*price)
                uploadRequest(resul)
                logD(resul.toString())
            }
            }

    }
    private fun uploadRequest(price: Long){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Pemesanan")
        builder.setMessage("Yakin Pesan space iklan ini? biaya yang harus Anda Bayar yaitu sebesar Rp.${NumberFormat.getNumberInstance(Locale.US).format(price) }")
        builder.setPositiveButton("Ya"){dialog,which ->

                val items = HashMap<String, Any>()
                items.put("ad_space", data.id)
                items.put("user", fAuth.currentUser!!.uid)
                items.put("image", "")
                val c = Calendar.getInstance().time
                val df = SimpleDateFormat("yyyy-MM-dd")
                val dfY = SimpleDateFormat("yyyy")
                val dfM = SimpleDateFormat("MM")
                val dfd = SimpleDateFormat("dd")
                val formattedDate = df.format(c)
                logD("order ${formattedDate}")
                items.put("order_date",formattedDate)
                items.put("order_status","Belum dibayar")
                items.put("start_date",editTanggal.text.toString())
                val date = df.parse(editTanggal.text.toString())
                var month=(date.month).plus(1)
                var year=  (dfY.format(date)).toInt()
                logD("yer ${year}")
                var day=date.date
                month=month+(Integer.parseInt(durasiEdit.text.toString()))
                if(month>12){
                    month=month-12
                    year=year+1
                }
                var mnth:String=month.toString()
                var dy:String=day.toString()
                if(month < 10){
                    mnth = "0" + mnth
                }
                if(day < 10){
                    dy = "0" + dy
                }
                endDate="${year}-${mnth}-${dy}"
                logD(endDate)
                items.put("end_date",endDate)
               db.collection("ad_space_order").document().set(items).addOnSuccessListener {
                    toast("Success Upload Request")
                   val intent = Intent(this, PaymentActivity::class.java)
                   startActivity(intent)
                }.addOnFailureListener {
                   toast("Oops! Error ${it.message}")
                }
        }
        builder.setNegativeButton("Tidak"){dialog,which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
