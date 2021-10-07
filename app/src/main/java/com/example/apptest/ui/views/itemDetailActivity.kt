package com.example.apptest.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.apptest.R
import com.example.apptest.data.DB.ApiDBInterface
import com.example.apptest.data.DB.Network
import com.example.apptest.data.repository.itemDetailRepository
import com.example.apptest.data.service.ApiDBObject
import com.example.apptest.data.service.imgUrl
import com.example.apptest.models.itemDetails
import com.example.apptest.ui.viewModels.itemViewModel
import kotlinx.android.synthetic.main.activity_item_detail.*
import java.text.NumberFormat
import java.util.*

class itemDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: itemViewModel
    private lateinit var itemRepository: itemDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val id: Int = intent.getIntExtra("id",1)

        val apiService: ApiDBInterface = ApiDBObject.getObject()
        itemRepository = itemDetailRepository(apiService)

        viewModel = getViewModel(id)

        viewModel.itemDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == Network.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == Network.ERROR) View.VISIBLE else View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    fun bindUI( it: itemDetails){
        supportActionBar?.title = it.title
        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.popularity.toString()
        movie_runtime.text = it.runtime.toString() + " minutes"
        movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        movie_revenue.text = formatCurrency.format(it.revenue)


        val imageURL = imgUrl + it.posterPath
        Glide.with(this)
            .load(imageURL)
            .into(iv_movie_poster);
        val imageCover = imgUrl + it.posterPath
        Glide.with(this)
            .load(imageCover)
            .into(iv_image_cover);
    }

    private fun getViewModel(id:Int): itemViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return itemViewModel(itemRepository, id) as T
            }
        })[itemViewModel::class.java]
    }
}