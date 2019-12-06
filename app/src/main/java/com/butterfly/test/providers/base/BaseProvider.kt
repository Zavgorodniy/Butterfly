package com.butterfly.test.providers.base

import com.butterfly.test.database.repositories.Repository
import com.butterfly.test.models.Model

abstract class BaseProvider<NetworkModel,
        DBModel,
        AppModelType : Comparable<AppModelType>,
        AppModel : Model<AppModelType>,
        NetworkModule,
        Repo : Repository<DBModel>>
    : BaseOnlineProvider<NetworkModel, AppModelType, AppModel, NetworkModule>() {

    val repository: Repo = this.initRepository()

    protected abstract fun initRepository(): Repo
}