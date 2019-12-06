package com.butterfly.test.models

import com.butterfly.test.ItemType
import kotlinx.android.parcel.Parcelize

interface ListItem : Model<String> {
    val title: String?
    val subtitle: String?
    val publisher: String?
    val imageLink: String?
    val type: ItemType
}

@Parcelize
data class TitleItemModel(
    override var id: String? = null,
    override val title: String? = null,
    override val subtitle: String? = null,
    override val publisher: String? = null,
    override val imageLink: String? = null,
    override val type: ItemType = ItemType.TITLE
) : ListItem
