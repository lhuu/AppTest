package com.example.apptest.data.DB

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.apptest.models.item
import com.example.apptest.ui.fragments.itemsListFragment
import io.reactivex.disposables.CompositeDisposable

class itemDataFactory(
    private val filter: String,
    private val apiService: ApiDBInterface,
    private val compositeDisposable: CompositeDisposable
)
    : DataSource.Factory<Int, item>() {

    val itemsLiveData =  MutableLiveData<itemData>()

    override fun create(): DataSource<Int, item> {
        val itemData = itemData(filter, apiService, compositeDisposable)

        itemsLiveData.postValue(itemData)
        return itemData
    }

    companion object {

        fun newInstance(): itemsListFragment {
            return itemsListFragment()
        }

    }
}