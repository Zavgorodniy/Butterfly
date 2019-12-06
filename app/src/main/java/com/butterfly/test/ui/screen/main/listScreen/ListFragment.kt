package com.butterfly.test.ui.screen.main.listScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.butterfly.test.R
import com.butterfly.test.extensions.appDrawable
import com.butterfly.test.extensions.appString
import com.butterfly.test.extensions.bindInterfaceOrThrow
import com.butterfly.test.models.ListItem
import com.butterfly.test.models.TitleItemModel
import com.butterfly.test.ui.base.list.BaseListFragment
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseListFragment<ListViewModel, ListItem>(),
    ListAdapterCallback {

    companion object {
        fun newInstance() = ListFragment()
    }

    override val viewModelClass = ListViewModel::class.java

    override val layoutId = R.layout.fragment_list

    private var testCallback: ListCallback? = null

    private var failedRequest: Boolean = false

    private val butterflyDataObserver = Observer<List<ListItem>> {
        onDataLoaded(it.toMutableList().apply {
            add(0, TitleItemModel(title = appString(R.string.butterfly)))
        })
        failedRequest = false
    }

    private val catDataObserver = Observer<List<ListItem>> {
        onDataLoaded(it.toMutableList().apply {
            add(0, TitleItemModel(title = appString(R.string.cat)))
        })
        failedRequest = false
    }

    private val errorListObserver = Observer<Any> {
        hideLoadingProgress()
        failedRequest = true
    }

    private val connectionObserver = Observer<Boolean> {
        if (it && failedRequest) {
            loadInitial()
        } else {
            hideProgress()
        }
    }

    private var adapter: ListAdapter? = null

    override fun getAdapter() =
        adapter ?: context?.let {
            ListAdapter(it, this)
        }.apply { adapter = this }

    override fun getScreenTitle(): Int = R.string.content

    override fun hasToolbar(): Boolean = true

    override fun needToShowBackNav() = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testCallback = bindInterfaceOrThrow(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        loadInitial()
    }

    override fun onDetach() {
        testCallback = null
        super.onDetach()
    }

    override fun observeLiveData() {
        with(viewModel) {
            butterflyLD.observe(this@ListFragment, butterflyDataObserver)
            catLD.observe(this@ListFragment, catDataObserver)
            connectionLiveData.observe(this@ListFragment, connectionObserver)
            errorLD.observe(this@ListFragment, errorListObserver)
        }
    }

    override fun onBackPressed(): Boolean {
        testCallback?.onNavigatedBack()
        return super.onBackPressed()
    }

    private fun setupUi() {
        rvRequest.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL).apply {
            appDrawable(R.drawable.item_divider)?.let { setDrawable(it) }
        })
    }

    override fun loadInitial() {
        showLoadingProgress()
        viewModel.run {
            loadButterfly()
            loadCat()
        }
    }

    override fun loadMoreData() = Unit

    override fun onItemLongClick(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
