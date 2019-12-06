package com.butterfly.test.network.bean.butterfly

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseButterfly(@JsonProperty("items")
                             val items: List<ButterflyBean>)