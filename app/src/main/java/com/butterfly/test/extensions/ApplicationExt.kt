package com.butterfly.test.extensions

import androidx.annotation.*
import com.butterfly.test.App

/**
 * Get string from resources
 *
 * @param resId [Int]
 *
 * @return [String]
 */
fun appString(@StringRes resId: Int) = App.instance.getString(resId)
