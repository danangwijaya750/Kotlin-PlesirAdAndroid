package com.dngwjy.plesirads.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Window
import android.widget.LinearLayout
import com.dngwjy.plesirads.R
import com.dngwjy.plesirads.data.adapters.DataAdapter
import com.dngwjy.plesirads.data.models.Space
import com.dngwjy.plesirads.presenters.ListSpacePresenter
import com.dngwjy.plesirads.util.logD
import com.dngwjy.plesirads.util.toast
import com.dngwjy.plesirads.views.ListSpaceView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_space.*

class ListSpaceActivity : AppCompatActivity(),ListSpaceView {
    private var spaceData:MutableList<Space> = mutableListOf()
    private var request:String=""
    lateinit var presenter:ListSpacePresenter
    lateinit var adapter:DataAdapter
    override fun isLoading(state: Boolean) {
        when(state){
            true-> swiper.isRefreshing=true
            false-> swiper.isRefreshing=false
        }
    }

    override fun showData(data: List<Space>) {
    spaceData.clear()
        spaceData.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_list_space)
        supportActionBar!!.hide()
        request=intent.getStringExtra("request")
        val db=FirebaseFirestore.getInstance()
        presenter= ListSpacePresenter(this,db)
        adapter= DataAdapter(spaceData){
            val intent=Intent(this,DetailSpaceActivity::class.java)
            intent.putExtra("data",it)
            startActivity(intent)
        }
        swiper.setOnRefreshListener {
            when(request){
                "all"->presenter.getAllSpace()
                "reklame"->presenter.getSpaceByType("owT1Ur6b6vPn6Ed5fUXu")
                "baliho"->presenter.getSpaceByType("ZstVfgFk0uS5jzJQGwGp")
                "poster"->presenter.getSpaceByType("G5TfHGvCNOOkJgFfHXip")
            }
        }
        val layMan=LinearLayoutManager(this)
        layMan.orientation=LinearLayoutManager.VERTICAL
        recView.layoutManager=layMan
        recView.adapter=adapter

        logD("reques $request")
        when(request){
            "all"->presenter.getAllSpace()
            "reklame"->presenter.getSpaceByType("owT1Ur6b6vPn6Ed5fUXu")
            "baliho"->presenter.getSpaceByType("ZstVfgFk0uS5jzJQGwGp")
            "poster"->presenter.getSpaceByType("G5TfHGvCNOOkJgFfHXip")
        }
        backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
