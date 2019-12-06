package com.butterfly.test.ui.screen.main.listScreen

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.butterfly.test.models.Butterfly
import com.butterfly.test.models.Cat
import com.butterfly.test.network.ConnectionLiveData
import com.butterfly.test.providers.ProviderInjector
import com.butterfly.test.ui.base.BaseViewModel
import io.reactivex.functions.Consumer

class ListViewModel(app: Application) : BaseViewModel(app) {

    val butterflyLD = MutableLiveData<List<Butterfly>>()

    val catLD = MutableLiveData<List<Cat>>()

    val connectionLiveData = ConnectionLiveData(app)

    private val butterflyProvider by lazy { ProviderInjector.getButterflyProvider() }

    private val catProvider by lazy { ProviderInjector.getCatProvider() }

    fun loadButterfly() {
        butterflyProvider.getAll()
            .doAsync(butterflyLD)
    }

    fun loadCat() {
        catProvider.getAll()
            .doAsync(catLD, Consumer {})
    }
}