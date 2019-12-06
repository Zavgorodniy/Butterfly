package com.butterfly.test.models.converters


import io.reactivex.ObservableTransformer
import java.lang.ref.WeakReference

class ObservableConverter<IN, OUT>(converter: Converter<IN, OUT>) {

    private val converterWR = WeakReference(converter)

    fun inToOut(): ObservableTransformer<IN, OUT> = ObservableTransformer { inObj ->
        inObj.map { converterWR.get()?.inToOut(it) }
    }

    fun outToIn(): ObservableTransformer<OUT, IN> = ObservableTransformer { outObj ->
        outObj.map { converterWR.get()?.outToIn(it) }
    }

    fun listInToOut(): ObservableTransformer<List<IN>, List<OUT>> = ObservableTransformer { inObj ->
        inObj.map { converterWR.get()?.listInToOut(it) }
    }

    fun listOutToIn(): ObservableTransformer<List<OUT>, List<IN>> = ObservableTransformer { outObj ->
        outObj.map { converterWR.get()?.listOutToIn(it) }
    }
}
