package com.butterfly.test.models.converters


import io.reactivex.SingleTransformer
import java.lang.ref.WeakReference

class SingleConverter<IN, OUT>(converter: Converter<IN, OUT>) {

    private val converterWR = WeakReference(converter)

    fun inToOut(): SingleTransformer<IN, OUT> = SingleTransformer { inObj ->
        inObj.map { converterWR.get()?.inToOut(it) }
    }

    fun outToIn(): SingleTransformer<OUT, IN> = SingleTransformer { outObj ->
        outObj.map { converterWR.get()?.outToIn(it) }
    }

    fun listInToOut(): SingleTransformer<List<IN>, List<OUT>> = SingleTransformer { inObj ->
        inObj.map { converterWR.get()?.listInToOut(it) }
    }

    fun listOutToIn(): SingleTransformer<List<OUT>, List<IN>> = SingleTransformer { outObj ->
        outObj.map { converterWR.get()?.listOutToIn(it) }
    }
}
