package com.butterfly.test.network.bean.cat

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseCat(@JsonProperty("all")
                       val all: List<CatBean>)