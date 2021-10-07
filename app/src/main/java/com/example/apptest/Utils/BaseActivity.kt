package com.example.apptest.Utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object BaseActivity {
    fun replaceFragmentInActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment, frameId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()
    }
}