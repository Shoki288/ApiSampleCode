package com.example.apisamplecode.productlist.viewmodels

import com.example.apisamplecode.api.ApiClient.retrofit
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ArticleListRepository {
    fun fetchArticles(tagId: String, page: Int): Single<List<Article>> {
        return retrofit.create(ArticleRequest::class.java)
            .fetchArticles(tagId, page, 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

// NOTE API叩いてるだけのクラスを作ったほうがいいかも？
interface ArticleRequest {

    @GET("tags/{tag_id}/items")
    fun fetchArticles(
        @Path("tag_id") tagId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<List<Article>>
}