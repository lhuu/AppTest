package com.example.apptest.data.DB

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.Pager
import com.example.apptest.data.service.firstPage
import com.example.apptest.models.item
import com.example.apptest.models.itemList
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import java.util.*

class itemData (private val filter: String, private val apiService: ApiDBInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, item>() {

    private var page = firstPage

    val networkState: MutableLiveData<Network> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, item>) {
        networkState.postValue(Network.LOADING)

        compositeDisposable.add(
            apiService.getData(filter, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if(it.totalPages >= params.key) {
                            callback.onResult(it.items, params.key+1)
                            networkState.postValue(Network.LOADED)
                            it?.let { it -> Log.e("itemsData", it.toString()) }
                        }
                        //else{
                        //    networkState.postValue(Network.ENDOFLIST)
                        //}
                    },
                    {
                        networkState.postValue(Network.ERROR)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, item>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, item>
    ) {
        networkState.postValue(Network.LOADING)

        compositeDisposable.add(
            apiService.getData(filter, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.items, null, page+1)
                        networkState.postValue(Network.LOADED)
                    },
                    {
                        networkState.postValue(Network.ERROR)
                        it.message?.let { it1 -> Log.e("MovieDataSource", it1) }
                    }
                )
        )
    }
}