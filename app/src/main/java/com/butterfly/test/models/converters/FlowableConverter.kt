package com.butterfly.test.models.converters


import io.reactivex.FlowableTransformer
import java.lang.ref.WeakReference

class FlowableConverter<IN, OUT>(converter: Converter<IN, OUT>) {

    private val converterWR = WeakReference(converter)

    fun inToOut(): FlowableTransformer<IN?, OUT> = FlowableTransformer { inObj ->
        inObj.map { converterWR.get()?.inToOut(it) }
    }

    fun outToIn(): FlowableTransformer<OUT?, IN> = FlowableTransformer { outObj ->
        outObj.map { converterWR.get()?.outToIn(it) }
    }

    fun listInToOut(): FlowableTransformer<List<IN>, List<OUT>> = FlowableTransformer { inObj ->
        inObj.map { converterWR.get()?.listInToOut(it) }
    }

    fun listOutToIn(): FlowableTransformer<List<OUT>, List<IN>> = FlowableTransformer { outObj ->
        outObj.map { converterWR.get()?.listOutToIn(it) }
    }
}
