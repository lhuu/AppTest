package com.example.apptest.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.example.apptest.R
import com.example.apptest.Utils.BaseActivity
import com.example.apptest.ui.fragments.popularListFragment
import com.example.apptest.ui.fragments.topRatedListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val popularListFragment = popularListFragment.newInstance()

        BaseActivity.replaceFragmentInActivity(
            supportFragmentManager, popularListFragment, fragment_container.id
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val popularFragment = popularListFragment.newInstance()
        val topRatedFragment = topRatedListFragment.newInstance()

        when (item.itemId) {
            R.id.popular_imenu -> {
                item.isChecked = true
                BaseActivity.replaceFragmentInActivity(
                    supportFragmentManager, popularFragment, fragment_container.id
                )
            }
            R.id.top_rated_imenu -> {
                item.isChecked = true
                BaseActivity.replaceFragmentInActivity(
                    supportFragmentManager, topRatedFragment, fragment_container.id
                )
            }
            R.id.action_search -> Toast.makeText(this, "buscar", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

}