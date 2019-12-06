package com.butterfly.test.providers

object ProviderInjector {

    private var catProvider: CatProvider? = null

    private var butterflyProvider: ButterflyProvider? = null

    fun getCatProvider(): CatProvider =
        catProvider ?: CatProviderImpl().apply {
            catProvider = this
        }

    fun getButterflyProvider(): ButterflyProvider =
        butterflyProvider ?: ButterflyProviderImpl().apply {
            butterflyProvider = this
        }
}
