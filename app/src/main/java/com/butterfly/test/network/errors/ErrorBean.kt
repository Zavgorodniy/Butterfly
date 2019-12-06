package com.butterfly.test.network.errors

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorBean(@JsonProperty("code")
                     val code: Int? = null,
                     @JsonProperty("key")
                     var key: String? = null,
                     @JsonProperty("message")
                     var message: String? = null)


