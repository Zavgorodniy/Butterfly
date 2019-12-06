package com.butterfly.test.providers

import com.butterfly.test.models.Cat
import com.butterfly.test.models.CatModel
import com.butterfly.test.models.converters.Converter
import com.butterfly.test.providers.base.BaseOnlineProvider
import com.butterfly.test.network.NetworkModuleInjector
import com.butterfly.test.network.bean.cat.CatBean
import com.butterfly.test.network.converters.CatConverterImpl
import com.butterfly.test.network.modules.CatModule
import io.reactivex.Single

interface CatProvider {

    fun getAll(): Single<List<Cat>>
}

class CatProviderImpl : BaseOnlineProvider<CatBean, String, Cat, CatModule>(),
    CatProvider {

    override val networkConverter: Converter<CatBean, Cat> = CatConverterImpl()

    override fun initNetworkModule() = NetworkModuleInjector.getCatModule()

    override fun initNewModel() = CatModel()

    override fun getAll(): Single<List<Cat>> =
        networkModule.getAll()
            .compose(networkConverter.single.listInToOut())
}