package com.example.apisamplecode

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleRequest {

    @GET("tags/{tag_id}/items")
    fun fetchArticles(
        @Path("tag_id") tagId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<List<Article>>
}