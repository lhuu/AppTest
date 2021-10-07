package com.example.apptest.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.apptest.data.DB.Network
import com.example.apptest.data.repository.itemDetailRepository
import com.example.apptest.data.repository.itemListRepository
import com.example.apptest.models.itemDetails
import io.reactivex.disposables.CompositeDisposable

class itemViewModel(private val itemDetailRepository: itemDetailRepository, id: Int) : ViewModel () {

    private val compositeDisposable = CompositeDisposable()

    val itemDetails : LiveData<itemDetails> by lazy {
        itemDetailRepository.fetchitemDetail(compositeDisposable, id)
    }

    val networkState : LiveData<Network> by lazy {
        itemDetailRepository.getItemDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}