package com.dngwjy.plesirads.views

import com.dngwjy.plesirads.data.models.Space

interface ListSpaceView {
    fun isLoading(state:Boolean)
    fun showData(data:List<Space>)
}