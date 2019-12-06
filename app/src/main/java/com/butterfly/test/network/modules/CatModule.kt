package com.butterfly.test.network.modules

import com.butterfly.test.network.api.CatApi
import com.butterfly.test.network.bean.cat.CatBean
import com.butterfly.test.network.modules.base.BaseRxModule
import com.butterfly.test.network.modules.base.NetworkErrorUtils
import io.reactivex.Single

interface CatModule {

    fun getAll(): Single<List<CatBean>>
}

class CatModuleImpl(api: CatApi) : BaseRxModule<CatApi>(api), CatModule {

    override fun getAll() =
        api.getAll()
            .onErrorResumeNext(NetworkErrorUtils.rxParseSingleError())
            .map { it.all }
}