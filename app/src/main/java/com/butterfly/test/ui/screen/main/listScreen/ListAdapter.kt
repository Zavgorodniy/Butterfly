package com.butterfly.test.ui.screen.main.listScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.butterfly.test.ItemType.BUTTERFLY
import com.butterfly.test.R
import com.butterfly.test.extensions.appString
import com.butterfly.test.extensions.find
import com.butterfly.test.extensions.hideIfEmpty
import com.butterfly.test.extensions.loadImageWithoutCrop
import com.butterfly.test.models.ListItem
import com.butterfly.test.ui.base.list.BaseSortedRecyclerViewAdapter
import com.butterfly.test.ui.base.list.BaseViewHolder
import java.lang.ref.WeakReference

interface ListAdapterCallback {

    fun onItemLongClick(text: String)
}

class ListAdapter(context: Context, callback: ListAdapterCallback) :
    BaseSortedRecyclerViewAdapter<ListItem, BaseViewHolder<ListItem>>(
        context,
        classType = ListItem::class.java
    ) {

    private val weakRefCallback = WeakReference(callback)

    override fun getItemViewType(position: Int): Int = getItem(position).type()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ListItem> =
        when (viewType) {
            BUTTERFLY() -> ButterflyVH.newInstance(inflater, parent, weakRefCallback)
            else -> CatVH.newInstance(inflater, parent, weakRefCallback)
        }

    override fun onBindViewHolder(holder: BaseViewHolder<ListItem>, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ButterflyVH(itemView: View, private val callbackRef: WeakReference<ListAdapterCallback>) :
        BaseViewHolder<ListItem>(itemView) {

        private val ivMain = itemView.find<ImageView>(R.id.ivMain)
        private val tvTitle = itemView.find<TextView>(R.id.tvTitle)
        private val tvSubtitle = itemView.find<TextView>(R.id.tvSubtitle)
        private val tvPublisher = itemView.find<TextView>(R.id.tvPublisher)

        companion object {
            internal fun newInstance(
                inflater: LayoutInflater,
                parent: ViewGroup?,
                weakRefCallback: WeakReference<ListAdapterCallback>
            ) = ButterflyVH(
                inflater.inflate(R.layout.item_butterfly, parent, false),
                weakRefCallback
            )
        }

        override fun bind(item: ListItem, position: Int) {
            item.run {
                ivMain.loadImageWithoutCrop(imageLink, R.drawable.ic_im_placeholder)
                tvTitle.hideIfEmpty(title)
                tvSubtitle.hideIfEmpty(subtitle)
                tvPublisher.hideIfEmpty(publisher)
                itemView.run {
                    setOnLongClickListener {
                        callbackRef.get()
                            ?.onItemLongClick(item.subtitle ?: appString(R.string.no_content))
                        true
                    }
                }
            }
        }
    }

    class CatVH(itemView: View, private val callbackRef: WeakReference<ListAdapterCallback>) :
        BaseViewHolder<ListItem>(itemView) {

        private val tvSubtitle = itemView.find<TextView>(R.id.tvSubtitle)
        private val tvUser = itemView.find<TextView>(R.id.tvUser)

        companion object {
            internal fun newInstance(
                inflater: LayoutInflater,
                parent: ViewGroup?,
                weakRefCallback: WeakReference<ListAdapterCallback>
            ) =
                CatVH(inflater.inflate(R.layout.item_cat, parent, false), weakRefCallback)
        }

        override fun bind(item: ListItem, position: Int) {
            item.run {
                tvSubtitle.hideIfEmpty(subtitle)
                tvUser.hideIfEmpty(publisher)
                itemView.run {
                    setOnLongClickListener {
                        callbackRef.get()
                            ?.onItemLongClick(item.subtitle ?: appString(R.string.no_content))
                        true
                    }
                }
            }
        }
    }
}