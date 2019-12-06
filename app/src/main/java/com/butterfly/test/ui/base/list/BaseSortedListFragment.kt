package com.butterfly.test.ui.base.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.butterfly.test.PAGE_LIMIT
import com.butterfly.test.R
import com.butterfly.test.VISIBLE_TRESHOLD
import com.butterfly.test.ui.base.BaseFragment
import com.butterfly.test.ui.base.BaseViewModel
import com.butterfly.test.extensions.setVisibility
import kotlinx.android.synthetic.main.fragment_list.*

abstract class BaseSortedListFragment<ViewModel : BaseViewModel, M : Any> :
        BaseFragment<ViewModel>(),
        SwipeRefreshLayout.OnRefreshListener,
        EndlessScrollListener.OnLoadMoreListener,
        PaginationListView {

    protected open val recyclerViewId = R.id.rvRequest

    protected open val noResultViewId = R.id.tvNoResult

    protected open val refreshLayoutId = R.id.srRequest

    protected open var pageLimit = PAGE_LIMIT

    protected open var visibleThreshold = VISIBLE_TRESHOLD

    protected var vNoResults: View? = null

    private var endlessScrollListener: EndlessScrollListener? = null

    private var refreshLayout: SwipeRefreshLayout? = null

    private var rvList: RecyclerView? = null

    /**
     * Get an adapter that extends [BaseSortedRecyclerViewAdapter].
     * @return an instance of [BaseSortedRecyclerViewAdapter].
     */
    protected abstract fun getAdapter(): BaseSortedRecyclerViewAdapter<M, *>?

    /**
     * This method is called to load the initial data.
     */
    protected abstract fun loadInitial()

    /**
     * This method is called to load the more data.
     */
    protected abstract fun loadMoreData()

    protected open fun getLayoutManager() = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    protected open fun getScrollDirection() =
        EndlessScrollListener.ScrollDirection.SCROLL_DIRECTION_DOWN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vNoResults = view.findViewById(noResultViewId)
        refreshLayout = view.findViewById(refreshLayoutId)
        refreshLayout?.apply {
            setOnRefreshListener(this@BaseSortedListFragment)
        }
        initList(view)
    }

    override fun onRefresh() {
        loadInitial()
    }

    override fun loadMore() {
        loadMoreData()
    }

    fun invalidateNoResults() {
        checkNoResults()
    }

    override fun onPaginationError() {
        refreshLayout?.isRefreshing = false
        endlessScrollListener?.updateNeedToLoad(true)
    }

    protected fun onDataLoaded(newData: List<M>, needToAddToAdapter: Boolean = true) {
        if (getAdapter()?.isEmpty() == false) {
            onDataRangeLoaded(newData, needToAddToAdapter)
        } else {
            onInitialDataLoaded(newData, needToAddToAdapter)
        }
        hideLoadingProgress()
    }

    /**
     * Set the initial data.
     */
    protected fun onInitialDataLoaded(newData: List<M>, needToAddToAdapter: Boolean = true) {
        refreshLayout?.isRefreshing = false
        endlessScrollListener?.reset()
        checkEndlessScroll(newData)
        if (needToAddToAdapter) {
            getAdapter()?.apply {
                clear()
                addAll(newData)
                notifyDataSetChanged()
            }
        }
        checkNoResults()
    }

    /**
     * Set the range data.
     */
    protected fun onDataRangeLoaded(newData: List<M>, needToAddToAdapter: Boolean = true) {
        checkEndlessScroll(newData)
        if (needToAddToAdapter) {
            getAdapter()?.apply {
                addAll(newData)
                if (newData.isNotEmpty()) notifyItemRangeInserted(itemCount, newData.size)
            }
        }
        endlessScrollListener?.updateNeedToLoad(true)
    }

    /**
     * Called when need to show progress view.
     */
    protected open fun showLoadingProgress() {
        refreshLayout?.isRefreshing = true
    }

    /**
     * Called when need to hide progress view.
     */
    protected open fun hideLoadingProgress() {
        refreshLayout?.isRefreshing = false
    }

    /**
     * Override this method if need check empty result or not
     */
    protected open fun checkNoResults(isEmptyResult: Boolean) = Unit

    protected open fun checkNoResults() {
        (getAdapter()?.isEmpty() == true).let { isEmpty ->
            checkNoResults(isEmpty)
            vNoResults?.setVisibility(isEmpty)
        }
    }

    /**
     * Call this method if need enable pagination.
     */
    protected fun enablePagination() {
        endlessScrollListener?.enable()
    }

    /**
     * Call this method if need disable pagination.
     */
    protected fun disablePagination() {
        endlessScrollListener?.disable()
    }

    private fun initList(view: View) {
        rvList = view.findViewById(recyclerViewId)
        rvList?.apply {
            adapter = this@BaseSortedListFragment.getAdapter()
            setHasFixedSize(false)
            layoutManager = this@BaseSortedListFragment.getLayoutManager()
            endlessScrollListener =
                EndlessScrollListener.create(
                    this,
                    visibleThreshold,
                    getScrollDirection()
                )
        }
    }

    private fun checkEndlessScroll(newData: List<M>) {
        endlessScrollListener?.onLoadMoreListener(if (newData.size < pageLimit) null else this)
    }
}