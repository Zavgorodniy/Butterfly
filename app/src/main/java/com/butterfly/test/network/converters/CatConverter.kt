package com.butterfly.test.network.converters

import com.butterfly.test.WHITE_SPACE
import com.butterfly.test.models.Cat
import com.butterfly.test.models.CatModel
import com.butterfly.test.models.converters.BaseInConverter
import com.butterfly.test.network.bean.cat.CatBean
import com.butterfly.test.network.bean.cat.CatUserNameBean

interface CatConverter

class CatConverterImpl : BaseInConverter<CatBean, Cat>(), CatConverter {

    override fun processConvertInToOut(inObject: CatBean?): Cat? = inObject?.run {
        CatModel(id, subtitle = text, publisher = user?.name?.let { getUserFullName(it) })
    }

    private fun getUserFullName(userName: CatUserNameBean) = StringBuilder().run {
        userName.first?.let {
            append(it)
            append(WHITE_SPACE)
        }
        userName.last?.let {
            append(it)
        }.toString()
    }
}