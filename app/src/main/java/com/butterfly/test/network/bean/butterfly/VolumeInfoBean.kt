package com.butterfly.test.network.bean.butterfly

import com.fasterxml.jackson.annotation.JsonProperty

data class VolumeInfoBean(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("subtitle")
    val subtitle: String?,
    @JsonProperty("publisher")
    val publisher: String?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("imageLinks")
    val imageLinks: ButterflyImageBean?
)