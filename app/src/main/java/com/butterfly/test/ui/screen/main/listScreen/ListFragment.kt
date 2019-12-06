package com.butterfly.test.ui.screen.main.listScreen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.butterfly.test.R
import com.butterfly.test.extensions.bindInterfaceOrThrow
import com.butterfly.test.models.Butterfly
import com.butterfly.test.models.Cat
import com.butterfly.test.models.ListItem
import com.butterfly.test.ui.base.list.BaseSortedListFragment

class ListFragment : BaseSortedListFragment<ListViewModel, ListItem>(),
    ListAdapterCallback {

    companion object {
        fun newInstance() = ListFragment()
    }

    override val viewModelClass = ListViewModel::class.java

    override val layoutId = R.layout.fragment_list

    private var testCallback: ListCallback? = null

    private val butterflyDataObserver = Observer<List<Butterfly>> {
        onDataLoaded(it)
    }

    private val catDataObserver = Observer<List<Cat>> {
        onDataLoaded(it)
    }

    private var adapter: ListAdapter? = null

    override fun getAdapter() =
        adapter ?: context?.let {
            ListAdapter(it, this)
        }.apply { adapter = this }

    override fun getScreenTitle(): Int = R.string.test

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
        }
    }

    override fun onBackPressed(): Boolean {
        testCallback?.onNavigatedBack()
        return super.onBackPressed()
    }

    private fun setupUi() = Unit

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
