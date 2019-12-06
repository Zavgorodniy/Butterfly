package com.butterfly.test.models

import com.butterfly.test.ItemType

interface ListItem : Model<String> {
    val title: String?
    val subtitle: String?
    val publisher: String?
    val imageLink: String?
    val type: ItemType
}
