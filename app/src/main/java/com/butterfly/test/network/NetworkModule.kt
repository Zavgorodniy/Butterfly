package com.butterfly.test.network

import com.butterfly.test.BuildConfig
import com.butterfly.test.network.clients.ServerClient
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule

object NetworkModule {

    val mapper: ObjectMapper =
        ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // send Date in "yyyy-MM-dd'T'HH:mm:ss.SSSZ" format
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(JodaModule())

    internal val catClient by lazy { ServerClient(BuildConfig.ENDPOINT_CAT) }

    internal val butterflyClient by lazy { ServerClient(BuildConfig.ENDPOINT_BUTTERFLY) }
}