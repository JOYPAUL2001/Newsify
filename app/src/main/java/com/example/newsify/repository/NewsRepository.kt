package com.example.newsify.repository

import com.example.newsify.api.RetrofitInstance
import com.example.newsify.db.ArticleDatabase
import com.example.newsify.models.Article

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(query, pageNumber)


    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getAllFavourites() = db.getArticleDao().getAllArticles()
}