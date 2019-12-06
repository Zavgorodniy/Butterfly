package com.butterfly.test.network

import com.butterfly.test.network.api.ButterflyApi
import com.butterfly.test.network.api.CatApi
import com.butterfly.test.network.modules.ButterflyModule
import com.butterfly.test.network.modules.ButterflyModuleImpl
import com.butterfly.test.network.modules.CatModule
import com.butterfly.test.network.modules.CatModuleImpl

object NetworkModuleInjector {

    private var catModule: CatModule? = null

    private var butterflyModule: ButterflyModule? = null

    fun getCatModule(): CatModule = catModule
        ?: CatModuleImpl(NetworkModule.catClient.retrofit.create(CatApi::class.java))
            .apply { catModule = this }

    fun getButterflyModule(): ButterflyModule = butterflyModule
        ?: ButterflyModuleImpl(NetworkModule.butterflyClient.retrofit.create(ButterflyApi::class.java))
            .apply { butterflyModule = this }
}