package com.butterfly.test.models

import android.os.Parcelable

interface Model<T> : Parcelable {
    var id: T?
}