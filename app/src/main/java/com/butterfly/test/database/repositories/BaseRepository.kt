package com.butterfly.test.database.repositories

import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor


abstract class BaseRepository<DBModel> : Repository<DBModel> {

    private val dataSetObservable = PublishProcessor.create<Unit>()
    private val itemObservable = PublishProcessor.create<DBModel>()
    private val itemsListObservable = PublishProcessor.create<List<DBModel>>()
    private val itemRemovedObservable = PublishProcessor.create<DBModel>()
    private val itemListRemovedObservable = PublishProcessor.create<List<DBModel>>()
    private val itemInsertObservable = PublishProcessor.create<DBModel>()

    /**
     * Notified when DataSet changed
     */
    fun setDataSetChanged() {
        dataSetObservable.onNext(Unit)
    }

    /**
     * Notified when item changed
     *
     * @param item Changed item of [DBModel] type
     */
    fun setItemChanged(item: DBModel) {
        itemObservable.onNext(item)
    }

    /**
     * Notified when item List changed
     *
     * @param items [List] of Changed items of [DBModel] type
     */
    fun setItemsListChanged(items: List<DBModel>) {
        itemsListObservable.onNext(items)
    }

    /**
     * Notified when item removed
     *
     * @param item Removed item of [DBModel] type
     */
    fun setItemRemoved(item: DBModel) {
        itemRemovedObservable.onNext(item)
    }

    /**
     * Notified when item List removed
     *
     * @param items [List] of Removed items of [DBModel] type
     */
    fun setItemsListRemoved(items: List<DBModel>) {
        itemListRemovedObservable.onNext(items)
    }

    /**
     * Notified when item insert
     *
     * @param item Inserted item of [DBModel] type
     */
    fun setItemInsert(item: DBModel) {
        itemInsertObservable.onNext(item)
    }

    /**
     * Returns [Flowable] [Unit] stream to subscribe for DataSetChanged event
     */
    override fun onDataSetChanged(): Flowable<Unit> = dataSetObservable


    /**
     * Returns Flowable to subscribe for ItemChanged events
     *
     * @return Instance of [Flowable] stream with model [DBModel] which was changed
     */
    override fun onItemChanged(): Flowable<DBModel> = itemObservable


    /**
     * Returns Flowable to subscribe for ItemListChanged event
     *
     * @return Instance of [Flowable] stream with [List] of models [DBModel] which was changed
     */
    override fun onItemsListChanged(): Flowable<List<DBModel>> = itemsListObservable


    /**
     * Returns Flowable to subscribe for ItemRemoved event
     *
     * @return Instance of [Flowable] stream with model [DBModel] which was removed
     */
    override fun onItemRemoved(): Flowable<DBModel> = itemRemovedObservable


    /**
     * Returns Flowable to subscribe for ItemListRemoved event
     *
     * @return Instance of [Flowable] stream with [List] of models [DBModel] which was removed
     */
    override fun onItemsRemoved(): Flowable<List<DBModel>> = itemListRemovedObservable

    /**
     * Returns Flowable to subscribe for ItemInsert event
     *
     * @return Instance of [Flowable] stream with model [DBModel] which was inserted
     */
    override fun onItemInsert(): Flowable<DBModel> = itemInsertObservable
}