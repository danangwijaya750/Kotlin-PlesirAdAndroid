package com.dngwjy.plesirads.data.models
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Space(
    var id:String,
    @set:PropertyName("name")
    @get:PropertyName("name")
    var namaReklame: String,
    @set:PropertyName("deskripsi")
    @get:PropertyName("deskripsi")
    var deskripsi:String,
    @set:PropertyName("rating")
    @get:PropertyName("rating")
    var rating:String,
    @set:PropertyName("space_type")
    @get:PropertyName("space_type")
    var spaceType:String,
    @set:PropertyName("address")
    @get:PropertyName("address")
    var address:String,
    @set:PropertyName("price")
    @get:PropertyName("price")
    var price:String,
    @set:PropertyName("visitor")
    @get:PropertyName("visitor")
    var visitors:Long,
    @set:PropertyName("phone")
    @get:PropertyName("phone")
    var phone:String,
    @set:PropertyName("photo")
    @get:PropertyName("photo")
    var photo:String,
    @set:PropertyName("height")
    @get:PropertyName("height")
    var heightSpace:Long,
    @set:PropertyName("width")
    @get:PropertyName("width")
    var widthSpace:Long
):Parcelable
{
    constructor():this("","","","","","","",0,"","",0,0)

}