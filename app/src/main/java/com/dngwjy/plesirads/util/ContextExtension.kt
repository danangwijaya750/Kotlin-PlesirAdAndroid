package com.dngwjy.plesirads.util

import android.content.Context
import android.widget.Toast
import java.util.regex.Pattern

fun Context.toast(msg:String?)= msg.let{
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
 fun Context.isEmail(data:String):Boolean{
    return Pattern.compile("^+.+@+.+\\..+$").matcher(data).matches()
}