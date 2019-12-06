package com.butterfly.test

enum class ItemType {
    BUTTERFLY,
    CAT;

    operator fun invoke() = ordinal
}