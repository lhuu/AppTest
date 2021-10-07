package com.example.apptest.data.DB

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apptest.models.itemDetails
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

class itemDetailData (private val apiService: ApiDBInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<Network>()
    val networkState: LiveData<Network>
        get() = _networkState

    private val _getItemDetailsResponse = MutableLiveData<itemDetails>()
    val itemDetailResponse: LiveData<itemDetails>
        get() = _getItemDetailsResponse

    fun fetchItenDetails(id: Int) {
        _networkState.postValue(Network.LOADING)

        try {
            compositeDisposable.add(
                apiService.getDataDetail(id)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _getItemDetailsResponse.postValue(it)
                            _networkState.postValue(Network.LOADED)
                            it?.let { it -> Log.e("itemData", it.toString()) }
                        },
                        {
                            _networkState.postValue(Network.ERROR)
                        }
                    )
            )
        }
        catch (e: Exception) {
            e.message?.let { Log.e("Exception Detail", it) }
        }
    }
}