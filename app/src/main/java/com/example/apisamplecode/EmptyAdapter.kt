package com.example.apisamplecode

import android.view.View
import com.example.apisamplecode.databinding.EmptyLayoutBinding
import com.xwray.groupie.viewbinding.BindableItem

class EmptyAdapter(private val message: String) : BindableItem<EmptyLayoutBinding>() {
    override fun bind(viewBinding: EmptyLayoutBinding, position: Int) {
        viewBinding.message.text = message
    }
    override fun getLayout(): Int = R.layout.empty_layout
    override fun initializeViewBinding(view: View): EmptyLayoutBinding = EmptyLayoutBinding.bind(view)
}