package com.butterfly.test.network.bean.butterfly

import com.fasterxml.jackson.annotation.JsonProperty

data class ButterflyBean(@JsonProperty("id")
                         val id: String?,
                         @JsonProperty("volumeInfo")
                         val volumeInfo: VolumeInfoBean?)