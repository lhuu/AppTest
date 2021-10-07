package com.example.apptest.data.repository

import androidx.lifecycle.LiveData
import com.example.apptest.data.DB.ApiDBInterface
import com.example.apptest.data.DB.Network
import com.example.apptest.data.DB.itemDetailData
import com.example.apptest.models.itemDetails
import io.reactivex.disposables.CompositeDisposable

class itemDetailRepository (private val apiService: ApiDBInterface) {

    lateinit var itemDetailData: itemDetailData

    fun fetchitemDetail(compositeDisposable: CompositeDisposable, id: Int) : LiveData<itemDetails> {
        itemDetailData = itemDetailData(apiService,compositeDisposable)
        itemDetailData.fetchItenDetails(id)
        return itemDetailData.itemDetailResponse
    }

    fun getItemDetailNetworkState(): LiveData<Network> {
        return itemDetailData.networkState
    }
}