package com.example.apptest.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apptest.R
import com.example.apptest.Utils.Constants
import com.example.apptest.data.DB.ApiDBInterface
import com.example.apptest.data.DB.Network
import com.example.apptest.data.repository.itemListRepository
import com.example.apptest.data.service.ApiDBObject
import com.example.apptest.models.item
import com.example.apptest.ui.adapters.itemListAdapter
import com.example.apptest.ui.viewModels.itemListViewModel
import kotlinx.android.synthetic.main.fragment_items_list.*

class topRatedListFragment : Fragment() {

    private lateinit var viewModel: itemListViewModel
    lateinit var listRepository: itemListRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_items_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val apiService : ApiDBInterface = ApiDBObject.getObject()

        listRepository = itemListRepository(apiService, Constants.TOPRATED)
        viewModel = getViewModel(Constants.TOPRATED)

        setListAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.findItem(R.id.top_rated_imenu)?.isChecked = true
    }

    private fun setListAdapter() {

        val listAdapter = itemListAdapter(this.requireContext())
        val gridLayoutManager = GridLayoutManager(this.requireContext(), 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = listAdapter.getItemViewType(position)
                if (viewType == listAdapter.itemsView) return  1
                else return 3
            }
        };
        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = listAdapter

        viewModel.itemList.observe(this, Observer<PagedList<item>> { items -> listAdapter.submitList(items) })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == Network.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == Network.ERROR) View.VISIBLE else View.GONE

            /*if (!viewModel.listIsEmpty()) {
                listAdapter.setNetworkState(it)
            }*/
        })
    }

    private fun getViewModel(filter: String): itemListViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return itemListViewModel(listRepository, filter) as T
            }
        })[itemListViewModel::class.java]
    }

    companion object {

        fun newInstance(): topRatedListFragment {
            return topRatedListFragment()
        }

    }
}