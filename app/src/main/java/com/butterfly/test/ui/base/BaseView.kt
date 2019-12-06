package com.butterfly.test.ui.base

interface BaseView {

    fun onError(error: Any)

    fun showProgress()

    fun hideProgress()
}