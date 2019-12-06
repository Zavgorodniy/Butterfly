package com.butterfly.test.network.converters

import com.butterfly.test.models.Butterfly
import com.butterfly.test.models.ButterflyModel
import com.butterfly.test.models.converters.BaseConverter
import com.butterfly.test.network.bean.butterfly.ButterflyBean

interface ButterflyConverter

class ButterflyConverterImpl : BaseConverter<ButterflyBean, Butterfly>(), ButterflyConverter {

    override fun processConvertInToOut(inObject: ButterflyBean?): Butterfly? =
        inObject?.volumeInfo?.run {
            ButterflyModel(
                inObject.id,
                title,
                subtitle,
                publisher,
                description,
                imageLinks?.smallThumbnail
            )
        }
}