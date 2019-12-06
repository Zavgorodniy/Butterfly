package com.butterfly.test.models.converters

abstract class BaseInConverter<IN : Any, OUT : Any> : BaseConverter<IN, OUT>() {

    override fun processConvertOutToIn(outObject: OUT?): IN? = Any() as IN

}