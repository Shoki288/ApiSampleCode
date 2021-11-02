package com.example.apisamplecode.productlist.viewmodels

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("likes_count")
    val likesCount: Int?,
    val title: String?,
    val user: User?
)

data class User(
    @SerializedName("profile_image_url")
    val profileImageUrl: String?
)

data class ArticleList(
    val articleList: List<Article>
)