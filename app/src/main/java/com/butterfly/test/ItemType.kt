package com.butterfly.test

enum class ItemType {
    BUTTERFLY,
    CAT,
    TITLE;

    operator fun invoke() = ordinal
}