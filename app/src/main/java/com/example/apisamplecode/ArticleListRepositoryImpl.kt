package com.example.apisamplecode

import com.example.apisamplecode.ApiClient.retrofit
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.create

class ArticleListRepository {
    fun fetchArticles(tagId: String, page: Int): Single<List<Article>> {
        return retrofit.create(ArticleRequest::class.java)
            .fetchArticles(tagId, page, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}