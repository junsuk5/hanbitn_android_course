package com.surivalcoding.mygallery

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PhotoAdapter(
    private val uris: List<Uri>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return uris.size
    }

    override fun createFragment(position: Int): Fragment {
        return PhotoFragment(uris[position])
    }
}