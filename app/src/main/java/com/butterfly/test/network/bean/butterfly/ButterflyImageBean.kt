package com.butterfly.test.network.bean.butterfly

import com.fasterxml.jackson.annotation.JsonProperty

data class ButterflyImageBean(@JsonProperty("smallThumbnail")
                              val smallThumbnail: String?)