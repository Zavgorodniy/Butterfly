package com.butterfly.test.providers.base

import com.butterfly.test.models.Model
import com.butterfly.test.models.converters.Converter

abstract class BaseOnlineProvider<NetworkModel,
        AppModelType : Comparable<AppModelType>,
        AppModel : Model<AppModelType>,
        NetworkModule> : Provider<AppModelType, AppModel> {

    val networkModule: NetworkModule = this.initNetworkModule()

    abstract val networkConverter: Converter<NetworkModel, AppModel>

    protected abstract fun initNetworkModule(): NetworkModule

    abstract fun initNewModel(): AppModel
}