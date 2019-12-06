package com.butterfly.test.network.bean.cat

import com.fasterxml.jackson.annotation.JsonProperty

data class CatUserNameBean(@JsonProperty("first")
                           var first: String?,
                           @JsonProperty("last")
                           var last: String?)