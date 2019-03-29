package com.dngwjy.plesirads.data.models
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Space(
    @set:PropertyName("ID")
    @get:PropertyName("ID")
    var id:String,
    @set:PropertyName("Name")
    @get:PropertyName("Name")
    var namaReklame: String,
    @set:PropertyName("Deskripsi")
    @get:PropertyName("Deskripsi")
    var deskripsi:String,
    @set:PropertyName("Rating")
    @get:PropertyName("Rating")
    var rating:String,
    @set:PropertyName("SpaceTypeID")
    @get:PropertyName("SpaceTypeID")
    var spaceType:Int,
    @set:PropertyName("Address")
    @get:PropertyName("Address")
    var address:String,
    @set:PropertyName("Price")
    @get:PropertyName("Price")
    var price:String,
    @set:PropertyName("Visitors")
    @get:PropertyName("Visitors")
    var visitors:String,
    @set:PropertyName("Phone")
    @get:PropertyName("Phone")
    var phone:String):Parcelable
{
    constructor():this("","","","",1,"","","","")
}