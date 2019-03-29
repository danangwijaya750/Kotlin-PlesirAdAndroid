package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.adapters.DataAdapter
import com.dngwjy.plesirads.data.models.Space
import com.dngwjy.plesirads.presenters.ListSpacePresenter
import com.dngwjy.plesirads.util.toast
import com.dngwjy.plesirads.views.ListSpaceView
import com.google.firebase.firestore.FirebaseFirestore

class ListSpaceActivity : AppCompatActivity(),ListSpaceView {
    var spaceData:MutableList<Space> = mutableListOf()
    lateinit var presenter:ListSpacePresenter
    lateinit var adapter:DataAdapter
    override fun isLoading(state: Boolean) {
        when(state){
            true-> toast("")
            false-> toast("")
        }
    }

    override fun showData(data: List<Space>) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_space)
        val db=FirebaseFirestore.getInstance()
        presenter= ListSpacePresenter(this,db)
        adapter= DataAdapter(spaceData){
            val intent=Intent(this,DetailSpaceActivity::class.java)
            intent.putExtra("data",it)
            startActivity(intent)
            finish()
        }
        val request=intent.getStringExtra("request")
        if(request==""){
            presenter.getAllSpace()
        }else{

        }
    }
}
