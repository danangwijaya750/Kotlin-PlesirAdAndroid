package com.dngwjy.plesirads.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var id:String="",
    var space_name:String="",
    var ad_space:String="",
    var end_date:String="",
    var image:String="",
    var order_date:String="",
    var order_status:String="",
    var start_date:String="",
    var harga_satuan:String="",
    var harga_total:String="",
    var user:String=""
) : Parcelable {
    constructor():this("","","","","","","","","","","")
}