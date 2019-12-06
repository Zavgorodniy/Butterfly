package com.butterfly.test.models

import com.butterfly.test.ItemType
import kotlinx.android.parcel.Parcelize

interface Cat : ListItem

@Parcelize
data class CatModel(override var id: String? = null,
                    override val title: String? = null,
                    override val subtitle: String? = null,
                    override val publisher: String? = null,
                    override val imageLink: String? = null,
                    override val type: ItemType = ItemType.CAT) : Cat