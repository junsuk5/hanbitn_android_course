package com.surivalcoding.mygallery

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class PhotoFragment(private val uri: Uri) : Fragment(R.layout.fragment_photo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().contentResolver.openFileDescriptor(uri, "r")?.use {
            val bitmap = BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
            view.findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
        }
    }
}