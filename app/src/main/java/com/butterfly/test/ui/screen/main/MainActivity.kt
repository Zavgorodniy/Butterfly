package com.butterfly.test.ui.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.butterfly.test.R
import com.butterfly.test.ui.base.BaseActivity
import com.butterfly.test.ui.screen.main.listScreen.ListCallback
import com.butterfly.test.ui.screen.main.listScreen.ListFragment

class MainActivity : BaseActivity<MainViewModel>(),
    ListCallback {

    companion object {
        fun start(context: Context) {
            with(context) {
                Intent(this, MainActivity::class.java).run { startActivity(this) }
            }
        }

        fun start(context: Context, intent: Intent) {
            with(intent) {
                setClass(context, MainActivity::class.java).let { context.startActivity(it) }
            }
        }
    }

    override val viewModelClass = MainViewModel::class.java

    override val layoutId = R.layout.activity_main

    override fun hasProgressBar() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openFragment()
    }

    override fun observeLiveData() = Unit

    override fun onNavigatedBack() {
        finish()
    }

    private fun openFragment() {
        replaceFragment(ListFragment.newInstance())
    }
}
