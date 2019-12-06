package com.butterfly.test.network.modules

import com.butterfly.test.network.api.ButterflyApi
import com.butterfly.test.network.bean.butterfly.ButterflyBean
import com.butterfly.test.network.modules.base.BaseRxModule
import com.butterfly.test.network.modules.base.NetworkErrorUtils
import io.reactivex.Single

interface ButterflyModule {

    fun getAll(): Single<List<ButterflyBean>>
}

class ButterflyModuleImpl(api: ButterflyApi) : BaseRxModule<ButterflyApi>(api), ButterflyModule {

    override fun getAll() =
        api.getAll()
            .onErrorResumeNext(NetworkErrorUtils.rxParseSingleError())
            .map { it.items }
}