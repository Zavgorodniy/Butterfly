package com.butterfly.test.extensions

import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.butterfly.test.App

/**
 * Get string from resources
 *
 * @param resId [Int]
 *
 * @return [String]
 */
fun appString(@StringRes resId: Int) = App.instance.getString(resId)

/**
 * Get string from resources
 *
 * @param stringId [Int]
 *
 * @param formatArgs Any
 *
 * @return [String]
 */
fun appString(@StringRes stringId: Int, vararg formatArgs: Any) =
        App.instance.getString(stringId, *formatArgs)

/**
 * Get integer from resources
 *
 * @param resId [Int]
 *
 * @return [Int]
 */
fun appInt(@IntegerRes resId: Int) = App.instance.getInteger(resId)

/**
 * Get string array from resources
 *
 * @param id [Int]
 *
 * @return [String[]]
 */
fun getStringArray(@ArrayRes id: Int) = App.instance.resources.getStringArray(id)

/**
 * Convert dimensions to pixels
 *
 * @param dimenRes [Int]
 *
 * @return [Int]
 */
fun getDimensionPixelSizeApp(@DimenRes dimenRes: Int) = App.instance.resources.getDimensionPixelSize(dimenRes)

fun appDrawable(@DrawableRes resId: Int) = ContextCompat.getDrawable(App.instance, resId)

/**
 * Convert color from resources
 *
 * @param colorRes [Int]
 *
 * @return [Int]
 */
fun appColor(@ColorRes colorRes: Int) = ContextCompat.getColor(App.instance, colorRes)