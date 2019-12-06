package com.butterfly.test.database.repositories

import io.reactivex.Flowable


interface Repository<DBModel> {
    /**
     * Returns [Flowable] [Unit] stream to subscribe for DataSetChanged event
     */
    fun onDataSetChanged(): Flowable<Unit>

    /**
     * Returns Flowable to subscribe for ItemChanged events
     *
     * @return Instance of [Flowable] stream with model [DBModel] which was changed
     */
    fun onItemChanged(): Flowable<DBModel>

    /**
     * Returns Flowable to subscribe for ItemListChanged event
     *
     * @return Instance of [Flowable] stream with [List] of models [DBModel] which was changed
     */
    fun onItemsListChanged(): Flowable<List<DBModel>>

    /**
     * Returns Flowable to subscribe for ItemRemoved event
     *
     * @return Instance of [Flowable] stream with model [DBModel] which was removed
     */
    fun onItemRemoved(): Flowable<DBModel>

    /**
     * Returns Flowable to subscribe for ItemListRemoved event
     *
     * @return Instance of [Flowable] stream with [List] of models [DBModel] which was removed
     */
    fun onItemsRemoved(): Flowable<List<DBModel>>

    /**
     * Returns Flowable to subscribe for ItemInsert event
     *
     * @return Instance of [Flowable] stream with model [DBModel] which was inserted
     */
    fun onItemInsert(): Flowable<DBModel>
}