package com.butterfly.test.network.bean.cat

import com.fasterxml.jackson.annotation.JsonProperty

data class CatBean(@JsonProperty("_id")
                   var id: String?,
                   @JsonProperty("text")
                   var text: String?,
                   @JsonProperty("user")
                   var user: CatUserBean?)