package com.example.apptest.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.example.apptest.data.DB.Network
import com.example.apptest.data.repository.itemListRepository
import com.example.apptest.models.item
import io.reactivex.disposables.CompositeDisposable

class itemListViewModel(private val itemListRepository: itemListRepository, filter: String) : ViewModel () {

    private val compositeDisposable = CompositeDisposable()

    val networkState : LiveData<Network> by lazy {
        itemListRepository.getitemsListNetworkState()
    }

    val  itemList : LiveData<PagedList<item>> by lazy {
        itemListRepository.fetchitemsList(compositeDisposable)
    }

    val  filterList : LiveData<PagedList<item>> by lazy {
        itemListRepository.fetchFilteritemsList(compositeDisposable, filter)
    }

    fun listIsEmpty(): Boolean {
        return itemList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}