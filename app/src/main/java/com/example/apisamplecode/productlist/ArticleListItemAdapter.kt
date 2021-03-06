package com.example.apisamplecode.productlist

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.apisamplecode.R
import com.example.apisamplecode.databinding.ArticleItemBinding
import com.example.apisamplecode.productlist.viewmodels.Article
import com.xwray.groupie.viewbinding.BindableItem

class ArticleListItemAdapter(private val item: Article, private val listener: ItemListener): BindableItem<ArticleItemBinding>() {
    private lateinit var glide: RequestManager

    override fun bind(viewBinding: ArticleItemBinding, position: Int) {
        viewBinding.apply {
            itemTitle.text = item.title
            itemLikesCount.text = item.likesCount.toString()
            val img = item.user?.profileImageUrl ?: R.color.black
            glide.load(img).into(profileImg)
            root.setOnClickListener {
               listener.onClickItem(position)
            }
        }
    }

    override fun getLayout(): Int = R.layout.article_item
    override fun initializeViewBinding(view: View): ArticleItemBinding {
        glide = Glide.with(view)
        return ArticleItemBinding.bind(view)
    }

    interface ItemListener { fun onClickItem(pos: Int) }
}