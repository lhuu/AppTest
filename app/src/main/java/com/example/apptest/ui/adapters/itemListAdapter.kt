package com.example.apptest.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptest.R
import com.example.apptest.data.DB.Network
import com.example.apptest.data.service.imgUrl
import com.example.apptest.models.item
import com.example.apptest.models.itemDetails
import com.example.apptest.ui.views.itemDetailActivity
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.loading_advice_view.view.*

class itemListAdapter (public val context: Context) : PagedListAdapter<item, RecyclerView.ViewHolder>(DataCallback()) {

    val itemsView = 1
    val networkView = 2

    private var networkState: Network? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        if (viewType == itemsView) {
            view = layoutInflater.inflate(R.layout.item_list, parent, false)
            return ItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.loading_advice_view, parent, false)
            return NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == itemsView) {
            (holder as ItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != Network.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            networkView
        } else {
            itemsView
        }
    }




    class DataCallback : DiffUtil.ItemCallback<item>() {
        override fun areItemsTheSame(oldItem: item, newItem: item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: item, newItem: item): Boolean {
            return oldItem == newItem
        }

    }


    class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: item?,context: Context) {
            itemView.cv_movie_title.text = item?.title
            itemView.cv_movie_release_date.text =  item?.releaseDate

            val moviePosterURL = imgUrl + item?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .into(itemView.cv_iv_movie_poster);

            itemView.setOnClickListener{
                val intent = Intent(context, itemDetailActivity::class.java)
                intent.putExtra("id", item?.id)
                context.startActivity(intent)
            }

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: Network?) {
            if (networkState != null && networkState == Network.LOADING) {
                itemView.progress_bar_item.visibility = View.VISIBLE;
            }
            else  {
                itemView.progress_bar_item.visibility = View.GONE;
            }

            if (networkState != null && networkState == Network.ERROR) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }
            /*else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                itemView.error_msg_item.visibility = View.VISIBLE;
                itemView.error_msg_item.text = networkState.msg;
            }*/
            else {
                itemView.error_msg_item.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: Network) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }

    }
}