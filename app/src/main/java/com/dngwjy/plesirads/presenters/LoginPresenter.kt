package com.dngwjy.plesirads.presenters

import com.dngwjy.plesirads.views.LoginView

class LoginPresenter(val view: LoginView) {
    fun doLogin(){
        view.isLoading(true)
        view.isLoading(false)
        view.showResult(true)
    }
}