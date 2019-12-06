package com.butterfly.test.providers.base

import com.butterfly.test.models.Model

interface Provider<AppModelType : Comparable<AppModelType>,
        AppModel : Model<AppModelType>>