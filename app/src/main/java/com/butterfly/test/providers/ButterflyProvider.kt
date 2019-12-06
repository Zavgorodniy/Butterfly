package com.butterfly.test.providers

import com.butterfly.test.models.Butterfly
import com.butterfly.test.models.ButterflyModel
import com.butterfly.test.models.converters.Converter
import com.butterfly.test.providers.base.BaseOnlineProvider
import com.butterfly.test.network.NetworkModuleInjector
import com.butterfly.test.network.bean.butterfly.ButterflyBean
import com.butterfly.test.network.converters.ButterflyConverterImpl
import com.butterfly.test.network.modules.ButterflyModule
import io.reactivex.Single

interface ButterflyProvider {

    fun getAll(): Single<List<Butterfly>>
}

class ButterflyProviderImpl : BaseOnlineProvider<ButterflyBean, String, Butterfly, ButterflyModule>(),
    ButterflyProvider {

    override val networkConverter: Converter<ButterflyBean, Butterfly> = ButterflyConverterImpl()

    override fun initNetworkModule() = NetworkModuleInjector.getButterflyModule()

    override fun initNewModel() = ButterflyModel()

    override fun getAll(): Single<List<Butterfly>> =
        networkModule.getAll()
            .compose(networkConverter.single.listInToOut())
}