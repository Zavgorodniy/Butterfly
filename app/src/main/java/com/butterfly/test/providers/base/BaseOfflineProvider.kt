package com.butterfly.test.providers.base

import com.butterfly.test.database.repositories.Repository
import com.butterfly.test.models.Model
import com.butterfly.test.models.converters.Converter


abstract class BaseOfflineProvider<T : Comparable<T>,
        DBModel,
        M : Model<T>,
        Repo : Repository<DBModel>> : Provider<T, M> {

    val repository: Repo = this.initRepository()

    abstract val databaseConverter: Converter<DBModel, M>

    protected abstract fun initRepository(): Repo
}