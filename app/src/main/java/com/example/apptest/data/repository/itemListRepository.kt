package com.example.apptest.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.apptest.data.DB.ApiDBInterface
import com.example.apptest.data.DB.Network
import com.example.apptest.data.DB.itemData
import com.example.apptest.data.DB.itemDataFactory
import com.example.apptest.data.service.itemsPage
import com.example.apptest.models.item
import io.reactivex.disposables.CompositeDisposable

class itemListRepository (private val apiService : ApiDBInterface, private val filter : String) {

    lateinit var itemList: LiveData<PagedList<item>>
    lateinit var itemsDataFactory: itemDataFactory

    fun fetchFilteritemsList (compositeDisposable: CompositeDisposable, filter: String) : LiveData<PagedList<item>> {
        itemsDataFactory = itemDataFactory(filter, apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(itemsPage)
            .build()

        itemList = LivePagedListBuilder(itemsDataFactory, config).build()

        return itemList
    }

    fun fetchitemsList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<item>> {
        itemsDataFactory = itemDataFactory(filter, apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(itemsPage)
            .build()

        itemList = LivePagedListBuilder(itemsDataFactory, config).build()

        return itemList
    }

    fun getitemsListNetworkState(): LiveData<Network> {
        return Transformations.switchMap<itemData, Network>(
            itemsDataFactory.itemsLiveData, itemData::networkState)
    }
}