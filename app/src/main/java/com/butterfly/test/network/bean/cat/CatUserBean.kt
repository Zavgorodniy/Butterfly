package com.butterfly.test.network.bean.cat

import com.fasterxml.jackson.annotation.JsonProperty

data class CatUserBean(
    @JsonProperty("_id")
    var id: String?,
    @JsonProperty("name")
    var name: CatUserNameBean?
)